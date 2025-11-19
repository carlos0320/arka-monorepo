package com.arka.ordermcsv.domain.usecases;

import com.arka.ordermcsv.domain.event.NotificationEvent;
import com.arka.ordermcsv.domain.event.OrderEvent;
import com.arka.ordermcsv.domain.event.OutboxEvent;
import com.arka.ordermcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.ordermcsv.domain.exception.NoConfirmedOrderFound;
import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.OrderStatus;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class DeliverOrderUseCase {
  private final OrderGateway orderGateway;
  private final OutboxEventGateway outboxEventGateway;
  private final ObjectMapper objectMapper;

  public DeliverOrderUseCase(OrderGateway orderGateway, OutboxEventGateway outboxEventGateway, ObjectMapper objectMapper) {
    this.orderGateway = orderGateway;
    this.outboxEventGateway = outboxEventGateway;
    this.objectMapper = objectMapper;
  }

  public void execute(Long orderId){
    try{
      Order shippedOrder = orderGateway.getOrderByOrderIdAndStatus(orderId, OrderStatus.SHIPPED.getValue())
              .orElseThrow(() -> new NoConfirmedOrderFound());

      shippedOrder.setDeliveredAt(LocalDateTime.now());
      shippedOrder.setStatus(OrderStatus.DELIVERED);

      Order orderUpdated = orderGateway.saveOrder(shippedOrder);

      OrderEvent orderEvent = OrderEvent.fromOrder(orderUpdated);

      // Notification event
      NotificationEvent notificationEvent = NotificationEvent.fromOrderEvent(orderEvent);
      String notificationPayload = objectMapper.writeValueAsString(notificationEvent);
      OutboxEvent notificationOutbox = OutboxEvent.from(
              "Notification",
              orderUpdated.getOrderId(),
              "OrderDeliveredNotificationEvent",
              notificationPayload
      );
      outboxEventGateway.save(notificationOutbox);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
