package com.example.emailsender.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.emailsender.Models.Email;
import com.example.emailsender.Services.MailService;

@Service
public class RabbitConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

    @Autowired
    private MailService eMailService;

    // Listening to the first queue
    @RabbitListener(queues = { "${rabbitmq.queue.name}" })
    public void consumer(String message) {
        LOGGER.info(String.format("String Consumer in action -> %s", message));

        eMailService.sendSimpleEmail(
                message,
                "Test Email",
                "Hello, your email is working successfully!");

    }

    // Listening to the second queue
    @RabbitListener(queues = { "${rabbitmq.queue.json.name}" })
    public void consumer1(Email email) {

        LOGGER.info(String.format("User class consumer in action -> %s", email));

        eMailService.sendSimpleEmail(
                email.getSendTo(),
                email.getSubject(),
                email.getContent());

    }
}
