package com.saga.order;

import com.saga.order.services.OrderServices;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = "com.order")
public class MockConfig {

//    @Bean
//    @Primary
//    public OrderServices orderServicesMock() {
//        OrderServices mock = Mockito.mock(OrderServices.class);
//        return mock;
//    }
}
