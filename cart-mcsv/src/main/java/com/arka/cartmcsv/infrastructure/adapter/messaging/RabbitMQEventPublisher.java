package com.arka.cartmcsv.infrastructure.adapter.messaging;

import com.arka.cartmcsv.application.event.EventPublisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements EventPublisher {
  @Value("${rabbitmq.cart.exchange}")
  private  String cartExchangeName;

  @Value("${rabbitmq.cart.routing.cancelled}")
  private String cartCancelledRoutingKey;

  @Value("${rabbitmq.cart.routing.confirmed}")
  private String cartConfirmedRoutingKey;

  @Value("${rabbitmq.cart.routing.abandoned}")
  private String cartAbandonedRoutingKey;

  private final AmqpTemplate amqpTemplate;

  public RabbitMQEventPublisher(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  @Override
  public void onCartCancelled(Object event) {
    amqpTemplate.convertAndSend(
            cartExchangeName,
            cartCancelledRoutingKey,
            event
    );
  }

  @Override
  public void onCartConfirmed(Object event) {
    amqpTemplate.convertAndSend(
            cartExchangeName,
            cartConfirmedRoutingKey,
            event
    );
  }
}
