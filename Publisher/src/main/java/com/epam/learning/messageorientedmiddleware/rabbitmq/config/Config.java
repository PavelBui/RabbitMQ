package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.repository.ProductRepository;
import org.springframework.amqp.core.*;
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
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("customer.fanout");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(deadLetterQueue);
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
    public Binding bindingFirst(FanoutExchange fanout,
                            Queue consumerQueueFirst) {
        return BindingBuilder.bind(consumerQueueFirst).to(fanout);
    }

    @Bean
    public Binding bindingSecond(FanoutExchange fanout,
                            Queue consumerQueueSecond) {
        return BindingBuilder.bind(consumerQueueSecond).to(fanout);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            var product1 = productRepository.save(
                    new Product(null,
                            "Rice",
                            "20kg",
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product1);
            var product2 = productRepository.save(
                    new Product(null,
                            "Buckwheat",
                            "10kg",
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product2);
            var product3 = productRepository.save(
                    new Product(null,
                            "Oatmeal",
                            "15kg",
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product3);
            var product4 = productRepository.save(
                    new Product(null,
                            "Millet",
                            "5kg",
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product4);
            var product5 = productRepository.save(
                    new Product(null,
                            "Lentils",
                            "10kg",
                            "Archived"
                    )
            );
            System.out.println("Preloading " + product5);
        };
    }

}
