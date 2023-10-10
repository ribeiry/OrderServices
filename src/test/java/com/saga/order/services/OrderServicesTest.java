package com.saga.order.services;

import com.saga.order.model.OrderDTO;
import com.saga.order.model.ProdutoDTO;
import com.saga.order.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc()
@SpringBootTest
public class OrderServicesTest {

    @InjectMocks
    OrderServices orderServices;

    @Mock
    OrderRepository repository;

    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.openMocks(this);
    }

    public OrderDTO getObject() {
        OrderDTO orderDTO = new OrderDTO();
        ProdutoDTO produtoDTO = new ProdutoDTO();

        orderDTO.setCodPedido(UUID.randomUUID());
        orderDTO.setCodCliente(UUID.randomUUID());
        orderDTO.setTotalPreco(8500);
        orderDTO.setStatusPedido("CANCELADO");

        produtoDTO.setCodProduto(UUID.randomUUID());
        produtoDTO.setQuantidade(1);
        produtoDTO.setDescProduto("XBOX Series ONE");
        produtoDTO.setPreco(5000.40F);

        List<ProdutoDTO> produtos = new ArrayList<>();
        produtos.add(produtoDTO);

        orderDTO.setProdutos(produtos);
        return orderDTO;
    }

    @Test
    public void testGetaOrderById(){
        OrderDTO orderDTO;
        orderDTO = getObject();
        when(repository.findById(orderDTO.getCodPedido())).thenReturn(Optional.ofNullable(orderDTO));
        OrderDTO orderRest = orderServices.findOne(orderDTO.getCodPedido());
        assertNotNull(orderRest);
        assertEquals(orderDTO.getCodPedido(),orderRest.getCodPedido());
        assertEquals(orderDTO.getTotalPreco(), orderRest.getTotalPreco());

    }
    @Test
    public void testGetAllOrders(){
        List<OrderDTO> pedidos = new ArrayList<>();
        pedidos.add(getObject());
        when(repository.findAll()).thenReturn(pedidos);
        List<OrderDTO> orderRest = orderServices.findAll();
        assertNotNull(orderRest);
        assertEquals(pedidos.get(0).getCodPedido(),pedidos.get(0).getCodPedido());

    }

    @Test
    public void testGetClientOrdersById(){
        List<OrderDTO> pedidos = new ArrayList<>();
        pedidos.add(getObject());
        UUID codCliente = pedidos.get(0).getCodCliente();
        when(repository.findByClient(codCliente)).thenReturn(pedidos);
        List<OrderDTO> orderRest = orderServices.findbyClient(codCliente);
        assertNotNull(orderRest);
        assertEquals(codCliente,pedidos.get(0).getCodCliente());
    }

    @Test
    public void testCreateaOrder(){
        OrderDTO orderDTO;
        orderDTO = getObject();
        UUID codPedido = orderDTO.getCodPedido();
        when(repository.findById(codPedido)).thenReturn(Optional.of(orderDTO));
        orderServices.save(orderDTO);
        OrderDTO orderRest = orderServices.findOne(codPedido);
        assertNotNull(orderRest);
        assertEquals(codPedido,orderRest.getCodPedido());
        assertEquals(orderDTO.getTotalPreco(), orderRest.getTotalPreco());
    }

    @Test
    public void testCancellaOrder(){
        OrderDTO orderDTO;
        orderDTO = getObject();
        UUID codPedido = orderDTO.getCodPedido();
        when(repository.findById(codPedido)).thenReturn(Optional.of(orderDTO));
        orderServices.CancelbyID(orderDTO.getCodPedido());
        OrderDTO orderRest = orderServices.findOne(codPedido);
        assertNotNull(orderRest);
        assertEquals(codPedido,orderRest.getCodPedido());
        assertEquals(orderDTO.getTotalPreco(), orderRest.getTotalPreco());
    }


}
