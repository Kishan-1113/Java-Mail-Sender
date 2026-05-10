package com.example.emailsender.Publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.emailsender.Models.User;

@Service
public class RabbitJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.route.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(User user) {
        LOGGER.info(String.format("Json Object sent to -> %s", user.toString()));
        rabbitTemplate.convertAndSend(exchangeName, routingJsonKey, user);
    }

}
