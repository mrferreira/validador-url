package com.mferreira.validadorurl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class ValidadorUrlApplication implements CommandLineRunner {

	public static final Logger LOGGER = LoggerFactory.getLogger(ValidadorUrlApplication.class.getName());

	@Autowired
	ApplicationContext applicationContext;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Initializing RabbitMQ Admin...");
		RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
		rabbitAdmin.initialize();
	}

	public static void main(String[] args) {
		SpringApplication.run(ValidadorUrlApplication.class, args);
	}
}
