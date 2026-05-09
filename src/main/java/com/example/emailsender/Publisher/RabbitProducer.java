package com.example.emailsender.Publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.route.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        LOGGER.info(String.format("Message sent to -> %s", message));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

}
