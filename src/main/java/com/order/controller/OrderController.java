package com.order.controller;

import com.order.exception.OrderNotFoundException;
import com.order.model.OrderDTO;
import com.order.services.OrderServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;


    @PostMapping
    @ResponseBody
    @Operation(summary = "Cria um pedido")
    @ApiResponse(responseCode = "201", description = "Pedido Criado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO pedido){
       orderServices.save(pedido);
       return  ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @GetMapping(value = "/")
    @Operation(summary = "Lista todos os pedido")
    @ApiResponse(responseCode = "200", description = "Encontrado Pedido")
    @ApiResponse(responseCode = "404", description = "Nao ha nenhum pedido ")
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
       List<OrderDTO> order = orderServices.findAll();
       if(order== null){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       else {
          return ResponseEntity.status(HttpStatus.FOUND).body(order);
       }
    }

    @GetMapping(value = "/{id}" )
    @Operation(summary = "Lista o pedido de acordo com o ID informado")
    @ApiResponse(responseCode =  "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> findaOrder(@PathVariable UUID id) throws OrderNotFoundException {
         OrderDTO orders = orderServices.findOne(id) ;
         if ( orders == null) {
            throw new OrderNotFoundException("id:" + id);
         }
         return ResponseEntity.status(HttpStatus.FOUND).body(orders);
    }
    @GetMapping(value = "/clients/{codCliente}" )
    @Operation(summary = "Lista o pedido de acordo com o ID de cliente informado")
    @ApiResponse(responseCode =  "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public ResponseEntity<List<OrderDTO>> findaOrderaClient(@PathVariable UUID codCliente) throws OrderNotFoundException {
        List<OrderDTO> orders = orderServices.findbyClient(codCliente);
        if ( orders == null) {
            throw new OrderNotFoundException("id:" + codCliente);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(orders);
    }

    @PutMapping(value = "/{codPedido}" )
    @Operation(summary = "Cancela o pedido de acordo com o ID de pedido informado")
    @ApiResponse(responseCode =  "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> cancelOrder(@PathVariable UUID codPedido) throws OrderNotFoundException {
        OrderDTO orders = orderServices.CancelbyID(codPedido);
        if ( orders == null) {
            throw new OrderNotFoundException("id:" + codPedido);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

}
