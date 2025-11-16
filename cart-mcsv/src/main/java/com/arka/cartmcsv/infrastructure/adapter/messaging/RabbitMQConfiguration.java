package com.arka.cartmcsv.infrastructure.adapter.messaging;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;

@Configuration
public class RabbitMQConfiguration {

  @Value("${rabbitmq.cart.exchange}")
  private  String cartExchangeName;

  @Value("${rabbitmq.cart.queues.cancelled}")
  private String cartCancelledQueueName;

  @Value("${rabbitmq.cart.queues.confirmed}")
  private String cartConfirmedQueueName;

  @Value("${rabbitmq.cart.queues.abandoned}")
  private String cartAbandonedQueueName;

  @Value("${rabbitmq.cart.routing.cancelled}")
  private String cartCancelledRoutingKey;

  @Value("${rabbitmq.cart.routing.confirmed}")
  private String cartConfirmedRoutingKey;

  @Value("${rabbitmq.cart.routing.abandoned}")
  private String cartAbandonedRoutingKey;


  // topics exchange
  @Bean
  public TopicExchange cartExchange() {
    return ExchangeBuilder.topicExchange(cartExchangeName)
            .durable(true)
            .build();
  }

  //queues
  @Bean
  public Queue cartCancelledQueue() {
    return QueueBuilder.durable(cartCancelledQueueName).build();
  }

  @Bean
  public Queue cartConfirmedQueue(){
    return QueueBuilder.durable(cartConfirmedQueueName).build();
  }

  @Bean
  public Queue cartAbandonedQueue(){
    return QueueBuilder.durable(cartAbandonedQueueName).build();
  }

  // bindings
  @Bean
  public Binding cartCancelledBinding() {
    return BindingBuilder
            .bind(cartCancelledQueue())
            .to(cartExchange())
            .with(cartCancelledRoutingKey);
  }

  @Bean
  public Binding cartConfirmedBinding(){
    return BindingBuilder
            .bind(cartConfirmedQueue())
            .to(cartExchange())
            .with(cartConfirmedRoutingKey);
  }

  @Bean
  public Binding cartAbandonedBinding(){
    return BindingBuilder
            .bind(cartAbandonedQueue())
            .to(cartExchange())
            .with(cartAbandonedRoutingKey);
  }

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

  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    rabbitTemplate.setExchange(cartExchangeName);
    return rabbitTemplate;
  }
}
