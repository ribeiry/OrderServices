package com.saga.order.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.order.model.OrderDTO;
import com.saga.order.model.ProdutoDTO;
import com.saga.order.services.OrderServices;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OrderController.class)
@ActiveProfiles("test")
public class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderServices orderServices;


    void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGetAllOrders() throws  Exception {
        List<OrderDTO> pedidos = getListObjectOrder();
        when(orderServices.findAll()).thenReturn(pedidos);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void testGetErrorAllOrders() throws  Exception {
        List<OrderDTO> pedidos = getListObjectOrder();
        when(orderServices.findAll()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAOrder() throws Exception{
        OrderDTO orderDTO = getObjectOrder();

        when(orderServices.findOne(orderDTO.getCodPedido())).thenReturn(orderDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderDTO.getCodPedido())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound());

    }
    @Test
    void testGetAClient() throws Exception{
        List<OrderDTO> pedidos = getListObjectOrder();
        when(orderServices.findAll()).thenReturn(pedidos);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/clients/{id}", pedidos.get(0).getCodCliente())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void testCreateAOrder() throws Exception{
        OrderDTO orderDTO = getObjectOrder();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapToJson(orderDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void testCancellAOrder() throws Exception{
        OrderDTO orderDTO = getObjectOrder();
        when(orderServices.CancelbyID(orderDTO.getCodPedido())).thenReturn(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}",orderDTO.getCodPedido())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }



    private String mapToJson(Object objects) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(objectMapper);
    }

    private OrderDTO getObjectOrder() {
        OrderDTO orderDTO = new OrderDTO();
        ProdutoDTO produtoDTO = new ProdutoDTO();
        orderDTO = new OrderDTO();


        orderDTO.setCodPedido(UUID.randomUUID());
        orderDTO.setCodCliente(UUID.randomUUID());
        orderDTO.setTotalPreco(8500);
        orderDTO.setStatusPedido("CANCELADO");

        produtoDTO = new ProdutoDTO();

        produtoDTO.setCodProduto(UUID.randomUUID());
        produtoDTO.setQuantidade(1);
        produtoDTO.setDescProduto("XBOX Series ONE");
        produtoDTO.setPreco(5000.40F);

        List<ProdutoDTO> produtos = new ArrayList<>();
        produtos.add(produtoDTO);

        orderDTO.setProdutos(produtos);


        return  orderDTO;
    }

    private List<OrderDTO> getListObjectOrder() {
        OrderDTO orderDTO = new OrderDTO();
        ProdutoDTO produtoDTO = new ProdutoDTO();
        List<OrderDTO> pedidos = new ArrayList<>();
        orderDTO = new OrderDTO();


        orderDTO.setCodPedido(UUID.randomUUID());
        orderDTO.setCodCliente(UUID.randomUUID());
        orderDTO.setTotalPreco(8500);
        orderDTO.setStatusPedido("CANCELADO");

        produtoDTO = new ProdutoDTO();

        produtoDTO.setCodProduto(UUID.randomUUID());
        produtoDTO.setQuantidade(1);
        produtoDTO.setDescProduto("XBOX Series ONE");
        produtoDTO.setPreco(5000.40F);

        List<ProdutoDTO> produtos = new ArrayList<>();
        produtos.add(produtoDTO);

        orderDTO.setProdutos(produtos);
        pedidos.add(orderDTO);

        return  pedidos;
    }
}