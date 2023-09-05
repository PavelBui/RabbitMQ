package com.epam.learning.messageorientedmiddleware.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.consumer-queue-first}")
    private String consumerQueueFirst;

    @Value("${spring.rabbitmq.consumer-queue-second}")
    private String consumerQueueSecond;

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("customer.fanout");
    }

//    @Bean
//    public Queue consumerQueueFirst() {
//        return new Queue(consumerQueue);
//    }

//    @Bean
//    public DirectExchange exchange() {
//        return new DirectExchange("tut.rpc");
//    }

    @Bean
    public Queue consumerQueueFirst() {
        return new Queue(consumerQueueFirst);
    }

    @Bean
    public Queue consumerQueueSecond() {
        return new Queue(consumerQueueSecond);
    }

//    @Bean
//    public Binding bindingFirst(FanoutExchange fanout,
//                            Queue consumerQueueFirst) {
//        return BindingBuilder.bind(consumerQueueFirst).to(fanout);
//    }
//
//    @Bean
//    public Binding bindingSecond(FanoutExchange fanout,
//                            Queue consumerQueueSecond) {
//        return BindingBuilder.bind(consumerQueueSecond).to(fanout);
//    }

}
