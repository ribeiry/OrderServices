package com.order.services;

import com.order.controller.OrderController;
import com.order.model.OrderDTO;
import com.order.model.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class OrderServicesTest {

    @Mock
    OrderServices orderServices;

    @InjectMocks
    OrderController orderController;

    OrderDTO orderDTO;
    ProdutoDTO produtoDTO;
    private final UUID codPedido = UUID.randomUUID();

    private  final  UUID codCliente = UUID.randomUUID();

    List<OrderDTO> pedidos;

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.openMocks(this);
        orderDTO = new OrderDTO();
        pedidos = new ArrayList<>();

        orderDTO.setCodPedido(codPedido);
        orderDTO.setCodCliente(codCliente);
        orderDTO.setTotalPreco(8500);
        orderDTO.setStatusPedido("FATURADO");

        produtoDTO = new ProdutoDTO();

        produtoDTO.setCodProduto(UUID.randomUUID());
        produtoDTO.setQuantidade(1);
        produtoDTO.setDescProduto("XBOX Series ONE");
        produtoDTO.setPreco(5000.40F);

        List<ProdutoDTO> produtos = new ArrayList<>();
        produtos.add(produtoDTO);

        orderDTO.setProdutos(produtos);

        pedidos.add(orderDTO);
    }

    @Test
    void testGetaOrderById(){
        when(orderServices.findOne(codPedido)).thenReturn(orderDTO);
        OrderDTO orderRest = orderServices.findOne(codPedido);
        assertNotNull(orderRest);
        assertEquals(codPedido,orderRest.getCodPedido());
        assertEquals(orderDTO.getTotalPreco(), orderRest.getTotalPreco());

    }

    @Test
    void testGetAllOrders(){
        when(orderServices.findAll()).thenReturn(pedidos);
        List<OrderDTO> orderRest = orderServices.findAll();
        assertNotNull(orderRest);
        assertEquals(codPedido,pedidos.get(0).getCodPedido());

    }

    @Test
    void testGetClientOrdersById(){
        when(orderServices.findbyClient(codCliente)).thenReturn(pedidos);
        List<OrderDTO> orderRest = orderServices.findbyClient(codCliente);
        assertNotNull(orderRest);
        assertEquals(codCliente,pedidos.get(0).getCodCliente());
    }

}
