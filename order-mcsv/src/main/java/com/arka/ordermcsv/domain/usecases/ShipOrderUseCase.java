package com.arka.ordermcsv.domain.usecases;

import com.arka.ordermcsv.domain.event.InventoryEvent;
import com.arka.ordermcsv.domain.event.NotificationEvent;
import com.arka.ordermcsv.domain.event.OrderEvent;
import com.arka.ordermcsv.domain.event.OutboxEvent;
import com.arka.ordermcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.OrderStatus;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ShipOrderUseCase {
  private final OrderGateway orderGateway;
  private final OutboxEventGateway outboxEventGateway;
  private final ObjectMapper objectMapper;

  public ShipOrderUseCase(OrderGateway orderGateway, OutboxEventGateway outboxEventGateway, ObjectMapper objectMapper) {
    this.orderGateway = orderGateway;
    this.outboxEventGateway = outboxEventGateway;
    this.objectMapper = objectMapper;
  }

  public void execute(Long orderId){
    try{
      Order confirmedOrder = orderGateway.getOrderByOrderIdAndStatus(orderId, OrderStatus.CONFIRMED.getValue())
              .orElseThrow(() -> new IllegalArgumentException("No confirmed order found"));

      confirmedOrder.setShippedAt(LocalDateTime.now());
      confirmedOrder.setStatus(OrderStatus.SHIPPED);

      Order orderUpdated = orderGateway.saveOrder(confirmedOrder);

      OrderEvent orderEvent = OrderEvent.fromOrder(orderUpdated);

      // Notification event
      NotificationEvent notificationEvent = NotificationEvent.fromOrderEvent(orderEvent);
      String notificationPayload = objectMapper.writeValueAsString(notificationEvent);
      OutboxEvent notificationOutbox = OutboxEvent.from(
              "Notification",
              orderUpdated.getOrderId(),
              "OrderShippedNotificationEvent",
              notificationPayload
      );
      outboxEventGateway.save(notificationOutbox);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
