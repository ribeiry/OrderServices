package com.order.services;

import com.order.repository.OrderRepository;
import com.order.model.OrderDTO;
import com.order.model.OrderMapper;
import com.order.model.ProdutoDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServices {


    @Autowired
    private OrderRepository repository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<OrderDTO> findAll(){
        List<OrderDTO> pedidos = repository.findAll();
        logger.info("RETURNING ALL ORDERS: " + pedidos);
        return OrderMapper.mapToDTOList(pedidos);
    }

    public OrderDTO findOne(UUID codPedido){
        logger.info("RETURNING A ORDERS: " + codPedido);
        return OrderMapper.mapToDTO(repository.findById(codPedido).get());
    }

    public List<OrderDTO> findbyClient(UUID codCliente){
        logger.info("RETURNING A ORDERS BY CLIENT ID CLIENT: " + codCliente);
        return  OrderMapper.mapToDTOList(repository.findByClient(codCliente));
    }

    @Transactional
    public  OrderDTO CancelbyID(UUID codPedido){
        logger.info("CANCELLING ORDER: " + codPedido);
        repository.cancelOrder(codPedido);
        return OrderMapper.mapToDTO(repository.findById(codPedido).get());
    }


    public void save(OrderDTO pedidoController){
        logger.info("SAVING ORDER: " + pedidoController);
        ProdutoDTO produto = new ProdutoDTO();
        float  valorTotal = 0;

        for (int i = 0; i < pedidoController.getProdutos().size(); i++) {
         pedidoController.getProdutos().get(i).setOrderDTO(pedidoController);
         valorTotal += pedidoController.getProdutos().get(i).getPreco();
        }
        pedidoController.setCodCliente(UUID.randomUUID());
        pedidoController.setTotalPreco(valorTotal);
        repository.save(pedidoController);
    }
}
