package com.epam.learning.messageorientedmiddleware.rabbitmq.service.impl;

import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessage;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchange;

    public void send(Product product) {
        ProductMessage productMessage = new ProductMessage(product);
        String key = getKey(product.getStatus());
        CorrelationData correlationData = new CorrelationData(String.valueOf(product.getId()));
        template.convertAndSend(topicExchange.getName(), key, productMessage, correlationData);
        System.out.println("Sent '" + productMessage + "'");
    }

    private String getKey(String productStatus) {
        StringBuilder key = new StringBuilder("customer.");
        if (productStatus.equals("Archived")) {
            key.append("first.archived");
        } else {
            key.append("next.")
                    .append(productStatus.toLowerCase());
        }
        return key.toString();
    }

}