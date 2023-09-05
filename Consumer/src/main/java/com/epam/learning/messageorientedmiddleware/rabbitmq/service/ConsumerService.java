package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
//@RabbitListener(queues = "${spring.rabbitmq.consumer-queue}")
public class ConsumerService {

//    @RabbitHandler
//    public void receive(String in) {
//        System.out.println("Received '" + in + "'");
//    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-first}")
    public void receiveFirst(String in) {
        System.out.println("First instance received '" + in + "'");
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-second}")
    public void receiveSecond(String in) {
        System.out.println("Second instance received '" + in + "'");
    }
}
