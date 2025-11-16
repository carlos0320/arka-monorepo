package com.arka.inventorymcsv.infrastructure.adapters.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfiguration {

  // === Exchange from ORDER microservice ===
  @Value("${rabbitmq.order.exchange}")
  private String orderExchangeName;

  // === Queues ===
  @Value("${rabbitmq.order.queues.confirmed.inventory}")
  private String orderConfirmedInventoryQueue;

  @Value("${rabbitmq.order.queues.cancelled}")
  private String orderCancelledQueue;

  // === Routing Keys ===
  @Value("${rabbitmq.order.routing.confirmed.inventory}")
  private String orderConfirmedInventoryRoutingKey;

  @Value("${rabbitmq.order.routing.cancelled}")
  private String orderCancelledRoutingKey;

  // === Queues ===
  @Bean
  public Queue orderConfirmedInventoryQueue() {
    return QueueBuilder.durable(orderConfirmedInventoryQueue).build();
  }

  @Bean
  public Queue orderCancelledQueue() {
    return QueueBuilder.durable(orderCancelledQueue).build();
  }

  // === Exchange (shared with Order service) ===
  @Bean
  public TopicExchange orderExchange() {
    return new TopicExchange(orderExchangeName);
  }

  // === Bindings ===
  @Bean
  public Binding bindOrderConfirmedInventoryQueue() {
    return BindingBuilder
            .bind(orderConfirmedInventoryQueue())
            .to(orderExchange())
            .with(orderConfirmedInventoryRoutingKey);
  }

  @Bean
  public Binding bindOrderCancelledQueue() {
    return BindingBuilder
            .bind(orderCancelledQueue())
            .to(orderExchange())
            .with(orderCancelledRoutingKey);
  }

  // === Admin & Message Conversion ===
  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin admin = new RabbitAdmin(connectionFactory);
    admin.setAutoStartup(true);
    return admin;
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
          ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(messageConverter());
    return factory;
  }
}
