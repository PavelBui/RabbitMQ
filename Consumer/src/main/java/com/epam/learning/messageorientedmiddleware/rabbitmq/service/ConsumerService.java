package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessage;
import com.epam.learning.messageorientedmiddleware.rabbitmq.bean.ProductMessagesValidator;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-first}", containerFactory = "rabbitFactory")
    public void receiveFirst(ProductMessage productMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws InterruptedException, IOException {
        System.out.println("First instance received '" + productMessage + "'");
        analyzeMessage(productMessage, channel, tag);
    }

    @RabbitListener(queues = "${spring.rabbitmq.consumer-queue-second}", containerFactory = "rabbitFactory")
    public void receiveSecond(ProductMessage productMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws InterruptedException, IOException {
        System.out.println("Second instance received '" + productMessage + "'");
        analyzeMessage(productMessage, channel, tag);
    }

    private void analyzeMessage(ProductMessage productMessage, Channel channel, long tag) throws InterruptedException, IOException {
        Long productId = productMessage.getId();
        String name = productMessage.getName();
        int weight = productMessage.getWeight();
        if (name.isEmpty() || weight <= 0) {
            if (!productMessagesValidatorMap.containsKey(productId)) {
                productMessagesValidatorMap.put(productId, new ProductMessagesValidator(productMessage));
            }
            ProductMessagesValidator productMessagesValidator = productMessagesValidatorMap.get(productId);
            productMessagesValidator.increaseAttemptsNumber();
            int attempt = productMessagesValidator.getAttemptsNumber();
            if (attempt > queueAskingLimit) {
                String message = productId.toString();
                this.template.convertAndSend(failedMessageQueue.getName(), message);
                System.out.println("Sent to failed message queue '" + message + "'");
                channel.basicAck(tag, false);
            } else {
                System.out.println("Message '" + productMessage + "' is failed. Sent acknowledge attempt " + attempt + " of " + queueAskingLimit);
                Thread.sleep(queueAskingTimeoutMs);
                channel.basicNack(tag, false, true);
            }
        } else {
            System.out.println("Client received success message '" + productMessage + "'");
            productMessagesValidatorMap.remove(productId);
            channel.basicAck(tag, false);
        }
    }

}