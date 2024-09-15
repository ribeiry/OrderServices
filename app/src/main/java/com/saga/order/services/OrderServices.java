package com.saga.order.services;

import com.saga.order.repository.OrderRepository;
import com.saga.order.model.OrderDTO;
import com.saga.order.model.OrderMapper;
import com.saga.order.model.ProdutoDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import static com.saga.order.constant.Constant.*;

@Service
public class OrderServices {


    @Autowired
    OrderRepository repository;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @CircuitBreaker(name="orderCircuit", fallbackMethod = "fallBackResponse")
    public List<OrderDTO> findAll(){
        List<OrderDTO> pedidos = repository.findAll();
        logger.info(MSG_LOG_SEARCH_ALL, pedidos);
        return OrderMapper.mapToDTOList(pedidos);
    }

    @CircuitBreaker(name="orderCircuit", fallbackMethod = "fallbackUnique")
    public OrderDTO findOne(UUID codPedido){
        logger.info(MSG_LOG_SEARCH_A_ORDER, codPedido);
        return OrderMapper.mapToDTO(repository.findById(codPedido).get());
    }
    @CircuitBreaker(name="orderCircuit", fallbackMethod = "fallBackResponse")
    public List<OrderDTO> findbyClient(UUID codCliente){
        logger.info(MSG_LOG_SEARCH_BY_ID, codCliente);
        return  OrderMapper.mapToDTOList(repository.findByClient(codCliente));
    }

    @Transactional
    @CircuitBreaker(name="orderCircuit", fallbackMethod = "fallbackUnique")
    public  OrderDTO CancelbyID(UUID codPedido){
        logger.info(MSG_LOG_CANCEL_ORDER,codPedido);
        repository.cancelOrder(codPedido);
        return OrderMapper.mapToDTO(repository.findById(codPedido).get());
    }

    @CircuitBreaker(name="orderCircuit", fallbackMethod = "errorFallback")
    public UUID save(OrderDTO pedidoController){
        logger.info(MSG_LOG_SAVING_ORDER, pedidoController);
        ProdutoDTO produto = new ProdutoDTO();
        float valorTotal = 0f;
        for (int i = 0; i < pedidoController.getProdutos().size(); i++) {
            pedidoController.getProdutos().get(i).setOrderDTO(pedidoController);
            valorTotal += pedidoController.getProdutos().get(i).getPreco();
        }
        pedidoController.setCodCliente(UUID.randomUUID());
        pedidoController.setCodPedido(UUID.randomUUID());
        pedidoController.setStatusPedido("ACCEPTED");
        pedidoController.setTotalPreco(valorTotal);
        repository.save(pedidoController);
        return pedidoController.getCodPedido();
    }

    public List<OrderDTO> fallBackResponse(Throwable ex){
        logger.error("Returning fallBackResponse");
        List<OrderDTO> order = new ArrayList<>();
        return order;
    }

    public OrderDTO fallbackUnique(UUID codPedido, Throwable e){
        logger.error("Returning fallbackUnique");
        return null;
    }

    public UUID errorFallback(Throwable e){

        logger.error("Returning errorFallback");
        String uuidString = "0123456789ABCEF00FECBA9876543210";
        long mostSig = Long.parseLong(uuidString.substring(0, 16), 16);
        long leastSig = Long.parseLong(uuidString.substring(16, 32), 16);
        UUID uuid = new UUID(mostSig, leastSig);

        System.out.println(uuid);
        return uuid;
    }
}
