package com.arka.ordermcsv.infrastructure.adapter.messaging;

import com.arka.ordermcsv.application.event.EventPublisher;
import com.arka.ordermcsv.domain.event.InventoryEvent;
import com.arka.ordermcsv.domain.event.NotificationEvent;
import com.arka.ordermcsv.domain.event.OrderEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements EventPublisher {

  @Value("${rabbitmq.order.exchange}")
  private String orderExchangeName;


  // routing keys
  @Value("${rabbitmq.order.routing.confirmed.inventory}")
  private String orderConfirmedInventoryRoutingKey;

  @Value("${rabbitmq.order.routing.created}")
  private String orderCreatedRoutingKey;

  @Value("${rabbitmq.order.routing.confirmed.notification}")
  private String orderConfirmedNotificationRoutingKey;

  @Value("${rabbitmq.order.routing.shipped}")
  private String orderShippedRoutingKey;

  @Value("${rabbitmq.order.routing.delivered}")
  private String orderDeliveredRoutingKey;

  private final AmqpTemplate amqpTemplate;

  public RabbitMQEventPublisher(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }


  @Override
  public void orderCreatedNotificationEvent(NotificationEvent notificationEvent) {
    amqpTemplate.convertAndSend(
            orderExchangeName,
            orderCreatedRoutingKey,
            notificationEvent
    );
  }

  @Override
  public void orderConfirmedInventoryEvent(InventoryEvent inventoryEvent) {
    amqpTemplate.convertAndSend(
            orderExchangeName,
            orderConfirmedInventoryRoutingKey,
            inventoryEvent
    );
  }

  @Override
  public void orderConfirmedNotificationEvent(NotificationEvent notificationEvent) {
    amqpTemplate.convertAndSend(
            orderExchangeName,
            orderConfirmedNotificationRoutingKey,
            notificationEvent
    );
  }

  @Override
  public void orderShippedNotificationEvent(NotificationEvent notificationEvent) {
    amqpTemplate.convertAndSend(
            orderExchangeName,
            orderShippedRoutingKey,
            notificationEvent
    );
  }

  @Override
  public void orderDeliveredNotificationEvent(NotificationEvent notificationEvent) {
    amqpTemplate.convertAndSend(
            orderExchangeName,
            orderDeliveredRoutingKey,
            notificationEvent
    );
  }
}
