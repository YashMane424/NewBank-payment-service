package com.newbank.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PAYMENT_EXCHANGE = "payment-exchange";
    public static final String PAYMENT_QUEUE = "payment-queue";
    public static final String PAYMENT_DLQ = "payment-dlq";

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENT_EXCHANGE, true, false);
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", PAYMENT_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "payment.dlq")
                .build();
    }

    @Bean
    public Queue paymentDLQ() {
        return new Queue(PAYMENT_DLQ, true);
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("payment.*");
    }

    @Bean
    public Binding paymentDLQBinding(Queue paymentDLQ, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentDLQ).to(paymentExchange).with("payment.dlq");
    }
}
