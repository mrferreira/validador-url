package com.mferreira.validadorurl.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PublisherService {

    @Value("${RESPONSE_EXCHANGE}")
    public String topicExchange;
    @Value("${RESPONSE_ROUTING_KEY}")
    public String routingKey;
    RabbitTemplate template;

    public PublisherService() {}

    @Autowired
    public PublisherService(RabbitTemplate template) {
        this.template = template;
    }

    public void publish(String message) {
        if(message == null || message.isEmpty()) {
            return;
        }
        //TODO:validate message
        template.convertAndSend(topicExchange, routingKey, message);
    }
}
