package com.saga.order.model;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderDTO mapToDTO(OrderDTO orderDTO){

        OrderDTO orderMapper = new OrderDTO();
        List<ProdutoDTO> produtoMapper = new ArrayList<>();

        orderMapper.setCodPedido(orderDTO.getCodPedido());
        orderMapper.setCodCliente(orderDTO.getCodCliente());
        orderMapper.setStatusPedido(orderDTO.getStatusPedido());
        orderMapper.setTotalPreco(orderDTO.getTotalPreco());

        for (int i = 0; i < orderDTO.getProdutos().size(); i++) {
            ProdutoDTO prod = new ProdutoDTO();
            prod.setCodProduto(orderDTO.getProdutos().get(i).getCodProduto());
            prod.setDescProduto(orderDTO.getProdutos().get(i).getDescProduto());
            prod.setPreco(orderDTO.getProdutos().get(i).getPreco());
            prod.setQuantidade(orderDTO.getProdutos().get(i).getQuantidade());

            produtoMapper.add(prod);
        }

        orderMapper.setProdutos(produtoMapper);

        return orderMapper;
    }

    public static List<OrderDTO> mapToDTOList(List<OrderDTO> orderDTOList){

        List<OrderDTO> PedidoList = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {

            OrderDTO orderMapper = new OrderDTO();
            List<ProdutoDTO> produtoMapper = new ArrayList<>();

            orderMapper.setCodPedido(orderDTO.getCodPedido());
            orderMapper.setCodCliente(orderDTO.getCodCliente());
            orderMapper.setStatusPedido(orderDTO.getStatusPedido());
            orderMapper.setTotalPreco(orderDTO.getTotalPreco());

            for (int i = 0; i < orderDTO.getProdutos().size(); i++) {
                ProdutoDTO prod = new ProdutoDTO();
                prod.setCodProduto(orderDTO.getProdutos().get(i).getCodProduto());
                prod.setDescProduto(orderDTO.getProdutos().get(i).getDescProduto());
                prod.setPreco(orderDTO.getProdutos().get(i).getPreco());
                prod.setQuantidade(orderDTO.getProdutos().get(i).getQuantidade());

                produtoMapper.add(prod);
            }

            orderMapper.setProdutos(produtoMapper);
            PedidoList.add(orderMapper);

        }

        return PedidoList;
    }
}
