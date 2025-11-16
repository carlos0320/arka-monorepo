package com.arka.ordermcsv.infrastructure.adapter.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Value("${rabbitmq.order.exchange}")
  private String orderExchangeName;

  // === Queues ===
  @Value("${rabbitmq.order.queues.created}")
  private String orderCreatedQueue;

  @Value("${rabbitmq.order.queues.confirmed.inventory}")
  private String orderConfirmedInventoryQueue;

  @Value("${rabbitmq.order.queues.confirmed.notification}")
  private String orderConfirmedNotificationQueue;

  @Value("${rabbitmq.order.queues.cancelled}")
  private String orderCancelledQueue;

  @Value("${rabbitmq.order.queues.shipped.notification}")
  private String orderShippedQueue;

  @Value("${rabbitmq.order.queues.delivered.notification}")
  private String orderDeliveredQueue;

  // === Routing Keys ===
  @Value("${rabbitmq.order.routing.created}")
  private String orderCreatedRoutingKey;

  @Value("${rabbitmq.order.routing.confirmed.inventory}")
  private String orderConfirmedInventoryRoutingKey;

  @Value("${rabbitmq.order.routing.confirmed.notification}")
  private String orderConfirmedNotificationRoutingKey;

  @Value("${rabbitmq.order.routing.cancelled}")
  private String orderCancelledRoutingKey;

  @Value("${rabbitmq.order.routing.shipped}")
  private String orderShippedRoutingKey;

  @Value("${rabbitmq.order.routing.delivered}")
  private String orderDeliveredRoutingKey;

  // === Queues ===
  @Bean
  public Queue orderCreatedQueue() {
    return QueueBuilder.durable(orderCreatedQueue).build();
  }

  @Bean
  public Queue orderConfirmedInventoryQueue() {
    return QueueBuilder.durable(orderConfirmedInventoryQueue).build();
  }

  @Bean
  public Queue orderConfirmedNotificationQueue() {
    return QueueBuilder.durable(orderConfirmedNotificationQueue).build();
  }

  @Bean
  public Queue orderCancelledQueue() {
    return QueueBuilder.durable(orderCancelledQueue).build();
  }

  @Bean
  public Queue orderShippedQueue() {
    return QueueBuilder.durable(orderShippedQueue).build();
  }

  @Bean
  public Queue orderDeliveredQueue() {
    return QueueBuilder.durable(orderDeliveredQueue).build();
  }

  // === Exchange ===
  @Bean
  public TopicExchange orderExchange() {
    return new TopicExchange(orderExchangeName);
  }

  // === Bindings ===
  @Bean
  public Binding bindOrderCreatedQueue() {
    return BindingBuilder.bind(orderCreatedQueue())
            .to(orderExchange())
            .with(orderCreatedRoutingKey);
  }

  @Bean
  public Binding bindOrderConfirmedInventoryQueue() {
    return BindingBuilder.bind(orderConfirmedInventoryQueue())
            .to(orderExchange())
            .with(orderConfirmedInventoryRoutingKey);
  }

  @Bean
  public Binding bindOrderConfirmedNotificationQueue() {
    return BindingBuilder.bind(orderConfirmedNotificationQueue())
            .to(orderExchange())
            .with(orderConfirmedNotificationRoutingKey);
  }

  @Bean
  public Binding bindOrderCancelledQueue() {
    return BindingBuilder.bind(orderCancelledQueue())
            .to(orderExchange())
            .with(orderCancelledRoutingKey);
  }

  @Bean
  public Binding bindOrderShippedQueue() {
    return BindingBuilder.bind(orderShippedQueue())
            .to(orderExchange())
            .with(orderShippedRoutingKey);
  }

  @Bean
  public Binding bindOrderDeliveredQueue() {
    return BindingBuilder.bind(orderDeliveredQueue())
            .to(orderExchange())
            .with(orderDeliveredRoutingKey);
  }

  // === Admin ===
  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }

  // === Message Converter ===
  @Bean
  public MessageConverter messageConverter() {
    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
    return converter;
  }

  // === Listener Factory ===
  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
          ConnectionFactory connectionFactory,
          SimpleRabbitListenerContainerFactoryConfigurer configurer) {

    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setMessageConverter(messageConverter());
    return factory;
  }
}
