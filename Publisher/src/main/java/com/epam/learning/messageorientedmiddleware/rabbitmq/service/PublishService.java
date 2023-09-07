package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutExchange;

    public void send(String message) {
        template.convertAndSend(fanoutExchange.getName(), "", message);
        System.out.println("Sent '" + message + "'");
    }
}
