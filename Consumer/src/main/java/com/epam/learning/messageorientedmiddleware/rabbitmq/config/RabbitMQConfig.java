package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.failed-message-queue}")
    private String failedMessageQueue;

    @Bean
    public Queue failedMessageQueue() {
        return new Queue(failedMessageQueue);
    }

}
