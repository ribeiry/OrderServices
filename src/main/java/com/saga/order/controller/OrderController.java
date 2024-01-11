package com.saga.order.controller;

import com.saga.order.exception.OrderNotFoundException;
import com.saga.order.model.OrderDTO;
import com.saga.order.services.OrderServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Operation(summary = "Cria um pedido")
    @ApiResponse(responseCode = "201", description = "Pedido Criado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderDTO pedido){
       UUID codOrder = orderServices.save(pedido);

       return  ResponseEntity.status(HttpStatus.CREATED).body(codOrder);
    }


    @GetMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Lista todos os pedido")
    @ApiResponse(responseCode = "302", description = "Encontrado Pedido")
    @ApiResponse(responseCode = "404", description = "Nao ha nenhum pedido ")
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
       List<OrderDTO> order = orderServices.findAll();
       if(order == null || order.isEmpty() ){
               return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       else {
          return ResponseEntity.status(HttpStatus.FOUND).body(order);
       }
    }

    @GetMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "Lista o pedido de acordo com o ID informado")
    @ApiResponse(responseCode =  "302", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> findaOrder(@PathVariable UUID id) throws Exception {
        OrderDTO order = null;
        try{
            order = orderServices.findOne(id) ;
            if ( order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(order);
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(order);
        }
        catch (OrderNotFoundException e){
            throw new OrderNotFoundException("id:" + id);
        }
        catch (Exception e){
            if("No value present".equalsIgnoreCase(e.getMessage())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(order);
            }
            throw  new Exception(e);
        }
    }
    @GetMapping(value = "/clients/{codCliente}",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "Lista o pedido de acordo com o ID de cliente informado")
    @ApiResponse(responseCode =  "302", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public ResponseEntity<List<OrderDTO>> findaOrderaClient(@PathVariable UUID codCliente) throws Exception {
        List<OrderDTO> orders = null;
        try {
           orders = orderServices.findbyClient(codCliente);
           if(orders.size() == 0 && orders.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orders);
           }
            return ResponseEntity.status(HttpStatus.FOUND).body(orders);

       }
        catch (Exception e){
            if("No value present".equalsIgnoreCase(e.getMessage())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orders);
            }
            throw  new Exception(e);
        }
    }

    @PutMapping(value = "/{codPedido}" ,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Cancela o pedido de acordo com o ID de pedido informado")
    @ApiResponse(responseCode =  "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> cancelOrder(@PathVariable UUID codPedido) throws OrderNotFoundException {
        OrderDTO orders = null;
        try{
            orders = orderServices.CancelbyID(codPedido);
            if ( orders == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orders);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
        catch (OrderNotFoundException e){
            throw new OrderNotFoundException("id:" + codPedido);
        }
    }

}
