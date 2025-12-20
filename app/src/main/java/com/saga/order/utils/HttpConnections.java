package com.saga.order.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class HttpConnections {

    private static String url =  "http://localhost:11434/api/generate";

    public String queryGemma3(String body, HttpHeaders headers, ResponseExtractor<String> responseExtractorRequest){

        RestTemplate restTemplate = new RestTemplate();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(new HttpEntity<>(body, headers));
        ResponseExtractor<String> responseExtractor = responseExtractorRequest;

        return restTemplate.execute(url, HttpMethod.POST, requestCallback, responseExtractor);
    }
}
