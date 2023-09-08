package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessage;
import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessagesValidator;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConsumerService {

    @Autowired
    private Queue failedMessageQueue;

    @Autowired
    private RabbitTemplate template;

    @Value("${rabbitmq.queue-asking-timeout-ms}")
    private int queueAskingTimeoutMs;

    @Value("${rabbitmq.queue-asking-limit}")
    private int queueAskingLimit;

    private static final Map<Long, ProductMessagesValidator> productMessagesValidatorMap = new HashMap<>();
    {
//        productMessagesValidatorMap.put(0L, new ProductMessagesValidator(null, null));
        productMessagesValidatorMap.put(0L, new ProductMessagesValidator(null));
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-first}")
    public void receiveFirst(String in) throws InterruptedException {
        System.out.println("First instance received '" + in + "'");
        ProductMessage productMessage = createProductMessage(in);
        analyzeMessage(productMessage);
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-second}")
    public void receiveSecond(String in) throws InterruptedException {
        System.out.println("Second instance received '" + in + "'");
        ProductMessage productMessage = createProductMessage(in);
        analyzeMessage(productMessage);
    }

    private ProductMessage createProductMessage(String message) {
        String[] messageParts = message.split(" ");
        return new ProductMessage(Long.valueOf(messageParts[0]), messageParts[1], Integer.valueOf(messageParts[2]));

    }

    private void analyzeMessage(ProductMessage productMessage) throws InterruptedException {
        Long productId = productMessage.getId();
        String name = productMessage.getName();
        int weight = productMessage.getWeight();
        if (weight <= 0 || weight > 100) {
            if (!productMessagesValidatorMap.containsKey(productId)) {
                productMessagesValidatorMap.put(productId, new ProductMessagesValidator(productMessage));
            }
            ProductMessagesValidator productMessagesValidator = productMessagesValidatorMap.get(productId);
            productMessagesValidator.increaseAttemptsNumber();
            int attempt = productMessagesValidator.getAttemptsNumber();
            if (attempt >= queueAskingLimit) {
                String message = productId.toString();
                this.template.convertAndSend(failedMessageQueue.getName(), message);
                System.out.println("Sent to failed message queue '" + message + "'");
            } else {
                System.out.println("Message '" + productMessage + "' is failed. Sent acknowledge attempt " + attempt + " of " + queueAskingLimit);
                Thread.sleep(queueAskingTimeoutMs);
                //acknowledge
            }
        } else {
            System.out.println("Client received success message '" + productMessage + "'");
            if (productMessagesValidatorMap.containsKey(productId)) {
                productMessagesValidatorMap.remove(productId);
            }
        }
    }

}
