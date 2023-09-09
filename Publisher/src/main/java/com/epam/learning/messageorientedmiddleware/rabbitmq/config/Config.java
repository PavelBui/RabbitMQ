package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.repository.ProductRepository;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Value("${spring.rabbitmq.consumer-queue-first}")
    private String consumerQueueFirst;

    @Value("${spring.rabbitmq.consumer-queue-second}")
    private String consumerQueueSecond;

    @Value("${spring.rabbitmq.dead-letter-queue}")
    private String deadLetterQueue;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("customer.topic");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(deadLetterQueue);
    }

    @Bean
    public Binding bindingFirst(TopicExchange topicExchange,
                                Queue consumerQueueFirst) {
        return BindingBuilder
                .bind(consumerQueueFirst)
                .to(topicExchange)
                .with("customer.first.archived");
    }

    @Bean
    public Binding bindingSecond(TopicExchange topicExchange,
                                 Queue consumerQueueSecond) {
        return BindingBuilder
                .bind(consumerQueueSecond)
                .to(topicExchange)
                .with("customer.next.*");
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(deadLetterQueue);
    }

    @Bean
    public Queue consumerQueueFirst() {
        Map<String, Object> args = new HashMap();
        args.put("x-dead-letter-exchange", deadLetterQueue);
        return new Queue(consumerQueueFirst, false, false, false, args);
    }

    @Bean
    public Queue consumerQueueSecond() {
        Map<String, Object> args = new HashMap();
        args.put("x-dead-letter-exchange", deadLetterQueue);
        return new Queue(consumerQueueSecond, false, false, false, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            var product1 = productRepository.save(
                    new Product(null,
                            "Rice",
                            20,
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product1);
            var product2 = productRepository.save(
                    new Product(null,
                            "Buckwheat",
                            10,
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product2);
            var product3 = productRepository.save(
                    new Product(null,
                            "Oatmeal",
                            15,
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product3);
            var product4 = productRepository.save(
                    new Product(null,
                            "Millet",
                            5,
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product4);
            var product5 = productRepository.save(
                    new Product(null,
                            "Lentils",
                            10,
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product5);
        };
    }

}
