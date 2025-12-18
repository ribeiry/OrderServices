package com.saga.order.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.order.configuration.AiConversationMemory;
import com.saga.order.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServicesGemma {

    @Autowired
    private OrderServices orderServices;
    private static String url =  "http://localhost:11434/api/generate";
    private static String responseStatic = "response";
    private AiConversationMemory memory = new AiConversationMemory(10);
    private List<OrderDTO> order = new ArrayList<>();


    private final Logger logger = LoggerFactory.getLogger(getClass());


    public String query(){

        order = orderServices.findAll();
        RestTemplate restTemplate = new RestTemplate();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(new HttpEntity<>(buildBody(), buildHeaders()));
        ResponseExtractor<String> responseExtractor = readResponse();

        return restTemplate.execute(url, HttpMethod.POST, requestCallback, responseExtractor);
    }

    private ResponseExtractor<String> readResponse (){
        AtomicReference<StringBuilder> fullResponse = new AtomicReference<>(new StringBuilder());


        return response -> {

            fullResponse.set(returnInJson(response));

            saveMemoryAI(fullResponse.get());

            return fullResponse.toString();
        };
    }

    private  StringBuilder returnInJson(ClientHttpResponse response) {
        StringBuilder fullResponse = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))){

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                JsonNode node = objectMapper.readTree(line);
                if (node.has(responseStatic)) {
                    fullResponse.append(node.get(responseStatic).asText());
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


        return fullResponse;
    }

    private void saveMemoryAI(StringBuilder fullResponse) {
        if(memory.hasSummary()){
            memory.addInteraction("IA: " + fullResponse.toString());
        }
        else {
            memory.saveSummary(fullResponse.toString());
        }
    }

    private String buildBody()  {
        boolean newOrders = order.size() < orderServices.findAll().size();
        try {

           if(!memory.hasSummary() && !newOrders) {

               return buildFirstAttemptPrompt(order);

           }
           else {
                return  buildOthersAttemptPrompt();
           }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private  HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        return  headers;
    }

    private String buildFirstAttemptPrompt(List<OrderDTO> order) throws JsonProcessingException {
        ObjectMapper mapperObject = new ObjectMapper();
        String orders = mapperObject.writeValueAsString(order);

        String promptResumo = """
                       Analise estes pedidos e produza um resumo estruturado contendo:
                       - produto mais caro
                       - produto mais vendido
                       - totais relevantes
                       - estatísticas importantes
                                  \s
                       Pedidos:
                       %s
                      \s""".formatted(orders);

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gemma3");
        payload.put("prompt", promptResumo);

        return mapperObject.writeValueAsString(payload);

    }

    private String buildOthersAttemptPrompt() throws JsonProcessingException {
        ObjectMapper mapperObject = new ObjectMapper();
        String pergunta = "Pergunta: Olhando os pedidos anteriores qual é a propabilidade de venda de qual produto ? ";

        String prompt = memory.buildPrompt(pergunta);

        Map<String, Object> payloadWithContext = new HashMap<>();

        payloadWithContext.put("model", "gemma3");
        payloadWithContext.put("prompt", prompt);

        return mapperObject.writeValueAsString(payloadWithContext);
    }
}
