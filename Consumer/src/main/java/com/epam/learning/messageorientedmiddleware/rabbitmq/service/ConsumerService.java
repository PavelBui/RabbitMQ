package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessage;
import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessagesValidator;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConsumerService {

    @Autowired
    private Queue failedMessageQueue;

    @Autowired
    private RabbitTemplate template;

    private static Map<Long, ProductMessagesValidator> productMessagesValidatorMap = new HashMap<>();
    {
        productMessagesValidatorMap.put(0L, new ProductMessagesValidator(null, null));
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-first}")
    public void receiveFirst(String in) {
        System.out.println("First instance received '" + in + "'");
        ProductMessage productMessage = createProductMessage(in);
        Long productId = productMessage.getId();
        if (productMessagesValidatorMap.containsKey(productId)) {
            productMessagesValidatorMap.get(productId).setProductMessageFirst(productMessage);
            analyzeMessages(productId);
        } else {
            productMessagesValidatorMap.put(productId, new ProductMessagesValidator(productMessage, null));
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-second}")
    public void receiveSecond(String in) {
        System.out.println("Second instance received '" + in + "'");
        ProductMessage productMessage = createProductMessage(in);
        Long productId = productMessage.getId();
        if (productMessagesValidatorMap.containsKey(productId)) {
            productMessagesValidatorMap.get(productId).setProductMessageSecond(productMessage);
            analyzeMessages(productId);
        } else {
            productMessagesValidatorMap.put(productId, new ProductMessagesValidator(null, productMessage));
        }
    }

    private ProductMessage createProductMessage(String message) {
        String[] messageParts = message.split(" ");
        return new ProductMessage(Long.valueOf(messageParts[0]), messageParts[1], messageParts[2]);

    }

    private void analyzeMessages(Long productId) {
        ProductMessagesValidator productMessagesValidator = productMessagesValidatorMap.get(productId);
        ProductMessage productMessageFirst = productMessagesValidator.getProductMessageFirst();
        ProductMessage productMessageSecond = productMessagesValidator.getProductMessageSecond();
        if (productMessageFirst.equals(productMessageSecond)) {
            String message = productId.toString() + " " + productMessageFirst.getName() + " = " + productMessageSecond.getName() + " and " + productMessageFirst.getValue() + " = " + productMessageSecond.getValue();
            this.template.convertAndSend(failedMessageQueue.getName(), message);
            System.out.println("Sent '" + message + "'");
        } else {
            //Something about bad acknowledgment
        }
    }

}
