package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @RabbitListener(queues = "${spring.rabbitmq.failed-message-queue}")
    public void receiveFailedMessage(String in) {
        System.out.println("Failed message is: '" + in + "'");
    }

}
