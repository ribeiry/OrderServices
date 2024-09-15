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

import static com.saga.order.constant.Constant.*;

@RestController
@RequestMapping(URL_PATH)
public class OrderController {

    @Autowired
    OrderServices orderServices;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Operation(summary = MSG_SWAGGER_CREATE)
    @ApiResponse(responseCode = "201", description = "Pedido Criado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderDTO pedido){
       UUID codOrder = orderServices.save(pedido);

       if(UUID.fromString("01234567-89ab-cef0-0fec-ba9876543210").equals(codOrder)){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(codOrder);
       }

       return  ResponseEntity.status(HttpStatus.CREATED).body(codOrder);
    }


    @GetMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = MSG_SWAGGER_LIST_ALL)
    @ApiResponse(responseCode = CODE_RETURN_SWAGGER_FOUND, description = "Encontrado Pedido")
    @ApiResponse(responseCode = CODE_RETURN_SWAGGER_NOT_FOUND, description = "Nao ha nenhum pedido ")
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
       List<OrderDTO> order;
       order = orderServices.findAll();
       if(order == null || order.isEmpty() ){
               return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       else {
          return ResponseEntity.status(HttpStatus.FOUND).body(order);
       }
    }

    @GetMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = MSG_SWAGGER_LIST_A_ORDER)
    @ApiResponse(responseCode =  CODE_RETURN_SWAGGER_FOUND, description = "Pedido encontrado")
    @ApiResponse(responseCode = CODE_RETURN_SWAGGER_NOT_FOUND, description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> findaOrder(@PathVariable UUID id) throws Exception {
        OrderDTO order = null;
        try{
            order = orderServices.findOne(id) ;
            if ( order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(order);
        }
        catch (OrderNotFoundException e){
            throw new OrderNotFoundException("id:" + id);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = URL_PATH_CLIENTS + "/{codCliente}",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = MSG_SWAGGER_LIST_BY_ID_C)
    @ApiResponse(responseCode =  CODE_RETURN_SWAGGER_FOUND, description = "Pedido encontrado")
    @ApiResponse(responseCode = CODE_RETURN_SWAGGER_NOT_FOUND, description = "Pedido com ID nao encontrado")
    public ResponseEntity<List<OrderDTO>> findaOrderaClient(@PathVariable UUID codCliente) throws Exception {
        List<OrderDTO> orders = null;
        try {

            orders = orderServices.findbyClient(codCliente);
           if(orders.isEmpty()){
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
    @Operation(summary = MSG_SWAGGER_CANCEL_ORDER)
    @ApiResponse(responseCode =  CODE_RETURN_SWAGGER_SUCCESS, description = "Pedido encontrado")
    @ApiResponse(responseCode = CODE_RETURN_SWAGGER_NOT_FOUND, description = "Pedido com ID nao encontrado")
    public  ResponseEntity<OrderDTO> cancelOrder(@PathVariable UUID codPedido) throws OrderNotFoundException {
        OrderDTO orders;
        try{
            orders = orderServices.CancelbyID(codPedido);
            if ( orders == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
        catch (OrderNotFoundException e){
            throw new OrderNotFoundException("id:" + codPedido);
        }
    }

}
