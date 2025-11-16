package com.arka.notificationmcsv.infrastructure.adapter.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Value("${rabbitmq.order.exchange}")
  private String orderExchangeName;

  @Value("${rabbitmq.order.queues.created}")
  private String orderCreatedQueue;

  @Value("${rabbitmq.order.queues.confirmed.notification}")
  private String orderConfirmedNotificationQueue;

  @Value("${rabbitmq.order.queues.shipped.notification}")
  private String orderShippedNotificationQueue;

  @Value("${rabbitmq.order.queues.delivered.notification}")
  private String orderDeliveredNotificationQueue;

  // Routing keys
  @Value("${rabbitmq.order.routing.created}")
  private String orderCreatedRoutingKey;

  @Value("${rabbitmq.order.routing.confirmed.notification}")
  private String orderConfirmedNotificationRoutingKey;

  @Value("${rabbitmq.order.routing.shipped}")
  private String orderShippedRoutingKey;

  @Value("${rabbitmq.order.routing.delivered}")
  private String orderDeliveredRoutingKey;

  @Bean
  public TopicExchange orderExchange() {
    return ExchangeBuilder.topicExchange(orderExchangeName).durable(true).build();
  }

  @Bean
  public Queue orderCreatedQueue() {
    return QueueBuilder.durable(orderCreatedQueue).build();
  }

  @Bean
  public Queue orderConfirmedNotificationQueue() {
    return QueueBuilder.durable(orderConfirmedNotificationQueue).build();
  }

  @Bean
  public Queue orderShippedNotificationQueue() {
    return QueueBuilder.durable(orderShippedNotificationQueue).build();
  }

  @Bean
  public Queue orderDeliveredNotificationQueue() {
    return QueueBuilder.durable(orderDeliveredNotificationQueue).build();
  }

  @Bean
  public Binding bindOrderCreatedQueue() {
    return BindingBuilder.bind(orderCreatedQueue())
            .to(orderExchange())
            .with(orderCreatedRoutingKey);
  }

  @Bean
  public Binding bindOrderConfirmedNotificationQueue() {
    return BindingBuilder.bind(orderConfirmedNotificationQueue())
            .to(orderExchange())
            .with(orderConfirmedNotificationRoutingKey);
  }

  @Bean
  public Binding bindOrderShippedNotificationQueue() {
    return BindingBuilder.bind(orderShippedNotificationQueue())
            .to(orderExchange())
            .with(orderShippedRoutingKey);
  }

  @Bean
  public Binding bindOrderDeliveredNotificationQueue() {
    return BindingBuilder.bind(orderDeliveredNotificationQueue())
            .to(orderExchange())
            .with(orderDeliveredRoutingKey);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
