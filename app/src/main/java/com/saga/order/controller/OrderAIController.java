package com.saga.order.controller;

import com.saga.order.services.OrderServicesGemma;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gemma")
public class OrderAIController {

    @Autowired
    OrderServicesGemma orderServicesGemma;


    @GetMapping( produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Operation(summary = "GEMMA3 Endpoint")
    @ApiResponse(responseCode = "200", description = "GEMMA3 Endpoint")
    public ResponseEntity<String> creatConversation(){

        return  ResponseEntity.status(HttpStatus.OK).body(orderServicesGemma.callGemma());
    }
}
