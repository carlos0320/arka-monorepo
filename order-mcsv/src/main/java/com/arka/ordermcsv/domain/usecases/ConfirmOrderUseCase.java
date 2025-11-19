package com.arka.ordermcsv.domain.usecases;

import com.arka.ordermcsv.domain.event.InventoryEvent;
import com.arka.ordermcsv.domain.event.NotificationEvent;
import com.arka.ordermcsv.domain.event.OrderEvent;
import com.arka.ordermcsv.domain.event.OutboxEvent;
import com.arka.ordermcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.ordermcsv.domain.exception.NoPendingOrderFoundException;
import com.arka.ordermcsv.domain.exception.UserIdInvalidException;
import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.OrderStatus;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ConfirmOrderUseCase {
  private final OrderGateway orderGateway;
  private final OutboxEventGateway outboxEventGateway;
  private final ObjectMapper objectMapper;

  public ConfirmOrderUseCase(OrderGateway orderGateway, OutboxEventGateway outboxEventGateway, ObjectMapper objectMapper) {
    this.orderGateway = orderGateway;
    this.outboxEventGateway = outboxEventGateway;
    this.objectMapper = objectMapper;
  }

  public void execute(Long orderId){
    if (orderId == null || orderId <= 0){
      throw new UserIdInvalidException();
    }

    try {
      Order pendingOrder = orderGateway.getOrderByOrderIdAndStatus(orderId, OrderStatus.PENDING.getValue())
              .orElseThrow(() -> new NoPendingOrderFoundException());

      pendingOrder.setConfirmedAt(LocalDateTime.now());
      pendingOrder.setStatus(OrderStatus.CONFIRMED);

      Order orderSaved = orderGateway.saveOrder(pendingOrder);

      OrderEvent orderEvent = OrderEvent.fromOrder(orderSaved);

      // Inventory event
      InventoryEvent inventoryEvent = InventoryEvent.fromOrderEvent(orderEvent);
      String inventoryPayload = objectMapper.writeValueAsString(inventoryEvent);
      OutboxEvent inventoryOutbox = OutboxEvent.from(
              "Inventory",
              orderSaved.getOrderId(),
              "OrderConfirmedInventoryEvent",
              inventoryPayload
      );
      outboxEventGateway.save(inventoryOutbox);

      // Notification event
      NotificationEvent notificationEvent = NotificationEvent.fromOrderEvent(orderEvent);
      String notificationPayload = objectMapper.writeValueAsString(notificationEvent);
      OutboxEvent notificationOutbox = OutboxEvent.from(
              "Notification",
              orderSaved.getOrderId(),
              "OrderConfirmedNotificationEvent",
              notificationPayload
      );
      outboxEventGateway.save(notificationOutbox);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

