package com.example.emailsender.Config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // First Queue Name
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    // Second Queue Name
    @Value("${rabbitmq.queue.json.name}")
    private String jsonqueueName;

    // Only Exchange, remains same for all queues
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    // Routing key for first queue
    @Value("${rabbitmq.route.key}")
    private String routingKey;

    // Routing key for second queue
    @Value("${rabbitmq.route.json.key}")
    private String jsonRoutingKey;

    // First Queue
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    // Second Queue
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonqueueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Connecting exchange with first queue
    // Binding between queue and exchange using routing key
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    // Connecting exchange with second queue
    // Binding between queue and exchange using routing key
    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(jsonRoutingKey);
    }

    // Spring autoconfiguration automatically configures these beans, and we do no
    // need to explicitly configure the beans for them

    // But as now we are sending json requests as message, not normal string, so
    // here we need to configure the RabbitTemplate class to support json transfer

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        return rabbitTemplate;
    }

    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}
