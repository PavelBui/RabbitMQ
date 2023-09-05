package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    @Value("${spring.rabbitmq.consumer-queue}")
//    private String consumerQueue;
//
//    @Bean
//    public Queue consumerQueue() {
//        return new Queue(consumerQueue);
//    }

}
