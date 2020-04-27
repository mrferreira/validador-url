package com.mferreira.validadorurl.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration implements RabbitListenerConfigurer , CommandLineRunner {

	public static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfiguration.class.getName());

	@Value("${INSERTION_QUEUE}")
	public String insertionQueueName;
	@Value("${VALIDATION_QUEUE}")
	public String validationQueueName;
	@Value("${NUMBER_OF_VALIDATION_CONSUMERS}")
	public int numberOfValidationConsumers;

	@Bean(name = "rabbitInsertionQueue")
	Queue insertionQueue() {
		return new Queue(insertionQueueName, false);
	}

	@Bean(name = "rabbitValidationQueue")
	Queue validationQueue() {
		Queue queue = new Queue(validationQueueName, false);
		return queue;
	}

	@Autowired
	GenericApplicationContext applicationContext;

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}


	@Override
	public void run(String... args) {
		LOGGER.info("Initializing RabbitMQ Admin...");
		RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
		rabbitAdmin.initialize();
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(
				new ConditionalRejectingErrorHandler(
						new InvalidPayloadRejectingFatalExceptionStrategy()));
		factory.setDefaultRequeueRejected(false);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}

	static class InvalidPayloadRejectingFatalExceptionStrategy implements FatalExceptionStrategy {

		private Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public boolean isFatal(Throwable t) {
			if (t instanceof ListenerExecutionFailedException &&
					(t.getCause() instanceof MessageConversionException ||
							t.getCause() instanceof MethodArgumentNotValidException)) {
				logger.warn("Fatal message conversion error; message rejected; it will be dropped: {}",
						((ListenerExecutionFailedException) t).getFailedMessage());
				return true;
			}
			return false;
		}
	}
}
