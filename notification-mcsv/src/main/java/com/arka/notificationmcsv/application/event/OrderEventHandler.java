package com.arka.notificationmcsv.application.event;

import com.arka.notificationmcsv.domain.model.NotificationType;
import com.arka.notificationmcsv.domain.usecase.SendOrderNotificationUseCase;
import com.arka.notificationmcsv.infrastructure.messaging.orderDto.OrderEventDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class OrderEventHandler implements OrderEvent{
  private final SendOrderNotificationUseCase sendOrderNotificationUseCase;

  public OrderEventHandler(SendOrderNotificationUseCase sendOrderNotificationUseCase) {
    this.sendOrderNotificationUseCase = sendOrderNotificationUseCase;
  }

  @Override
  @Transactional
  public void onOrderCreated(OrderEventDto orderEventDto) {
    Map<String, Object> data = buildMessageData(orderEventDto);
    data.put("status",NotificationType.CREATED.getValue());
    sendOrderNotificationUseCase.execute(
            orderEventDto.getUserId(),
            orderEventDto.getContactEmail(),
            NotificationType.CREATED,
            data
    );
  }

  @Override
  @Transactional
  public void onOrderConfirmed(OrderEventDto orderEventDto) {
    Map<String, Object> data = buildMessageData(orderEventDto);
    data.put("status",NotificationType.CONFIRMED.getValue());
    sendOrderNotificationUseCase.execute(
            orderEventDto.getUserId(),
            orderEventDto.getContactEmail(),
            NotificationType.CONFIRMED,
            data
    );
  }

  @Override
  @Transactional
  public void onOrderShipped(OrderEventDto orderEventDto) {
    Map<String, Object> data = buildMessageData(orderEventDto);
    data.put("status",NotificationType.SHIPPED.getValue());
    sendOrderNotificationUseCase.execute(
            orderEventDto.getUserId(),
            orderEventDto.getContactEmail(),
            NotificationType.SHIPPED,
            data
    );
  }

  @Override
  @Transactional
  public void onOrderDelivered(OrderEventDto orderEventDto) {
    Map<String, Object> data = buildMessageData(orderEventDto);
    data.put("status",NotificationType.DELIVERED.getValue());
    sendOrderNotificationUseCase.execute(
            orderEventDto.getUserId(),
            orderEventDto.getContactEmail(),
            NotificationType.DELIVERED,
            data
    );
  }

  private Map<String, Object> buildMessageData(OrderEventDto dto) {
    Map<String, Object> data = new HashMap<>();
    data.put("customerName", dto.getCustomerName());
    data.put("orderId", dto.getOrderId().toString());
    data.put("totalCost", dto.getTotalCost().toPlainString());
    data.put("userName", dto.getCustomerName());
    data.put("shippingAddress", dto.getShippingAddress());
    data.put("items", dto.getItems());
    return data;
  }
}
