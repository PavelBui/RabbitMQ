package com.epam.learning.messageorientedmiddleware.rabbitmq.service.impl;

import com.epam.learning.messageorientedmiddleware.rabbitmq.exception.ProductNotFoundException;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${spring.rabbitmq.failed-message-queue}")
    public void receiveFailedMessage(String in) {
        System.out.println("Failed message is: '" + in + "'");
        Long id = Long.valueOf(in);
        Product product = productService.getProduct(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setStatus("Discarded");
        productService.updateProduct(product);
    }

    @RabbitListener(queues = "${spring.rabbitmq.dead-letter-queue}")
    public void receiveDeadMessage(String in) {
        System.out.println("Dead message is: '" + in + "'");
        Long id = getIdFromMessage(in);
        Product product = productService.getProduct(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setStatus("Returned");
        productService.updateProduct(product);
    }

    private Long getIdFromMessage(String message) {
        String[] messageParts = message.split(" ");
        return Long.valueOf(messageParts[0]);

    }

}