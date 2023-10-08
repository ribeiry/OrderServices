package com.order.repository;

import com.order.model.OrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.UUID;


public interface OrderRepository extends JpaRepository<OrderDTO, UUID> {
    @Query(value = "SELECT * FROM ORDERDTO WHERE COD_CLIENTE = ?1", nativeQuery = true)
    List<OrderDTO> findByClient(@Param("codCliente")UUID codCliente);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDTO SET STATUS_PEDIDO = 'CANCELADO' WHERE COD_PEDIDO = :codPedido",nativeQuery = true)
    void cancelOrder(@Param("codPedido")UUID codPedido);
}
