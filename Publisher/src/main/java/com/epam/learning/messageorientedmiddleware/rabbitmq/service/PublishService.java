package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutExchange;

//    @Autowired
//    private Queue consumerQueue;

    public void send(String message) {
        template.convertAndSend(fanoutExchange.getName(), "", message);
//        this.template.convertAndSend(consumerQueue.getName(), message);
        System.out.println("Sent '" + message + "'");
    }
}
