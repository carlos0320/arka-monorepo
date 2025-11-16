package com.arka.notificationmcsv.application.event;

import com.arka.notificationmcsv.infrastructure.messaging.orderDto.OrderEventDto;

public interface OrderEvent {
  void onOrderCreated(OrderEventDto orderEventDto);
  void onOrderConfirmed(OrderEventDto orderEventDto);
  void onOrderShipped(OrderEventDto orderEventDto);
  void onOrderDelivered(OrderEventDto orderEventDto);
}
