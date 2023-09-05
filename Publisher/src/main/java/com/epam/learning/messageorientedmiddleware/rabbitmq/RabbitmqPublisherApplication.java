package com.epam.learning.messageorientedmiddleware.rabbitmq;

import com.epam.learning.messageorientedmiddleware.rabbitmq.service.PublishService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RabbitmqPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqPublisherApplication.class, args);
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RabbitmqPublisherApplication.class, args);

		PublishService publishService = configurableApplicationContext.getBean(PublishService.class);

		publishService.send("Hello everybody!");
	}

}
