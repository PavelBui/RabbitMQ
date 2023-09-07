package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.repository.ProductRepository;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${spring.rabbitmq.consumer-queue-first}")
    private String consumerQueueFirst;

    @Value("${spring.rabbitmq.consumer-queue-second}")
    private String consumerQueueSecond;

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("customer.fanout");
    }

    @Bean
    public Queue consumerQueueFirst() {
        return new Queue(consumerQueueFirst);
    }

    @Bean
    public Queue consumerQueueSecond() {
        return new Queue(consumerQueueSecond);
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
