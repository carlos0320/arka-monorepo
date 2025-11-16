package com.arka.ordermcsv.domain.event;

import com.arka.ordermcsv.domain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
  private Long orderId;
  private String customerName;
  private String contactEmail;
  private Long userId;
  private String shippingAddress;
  private BigDecimal totalCost;
  private List<OrderEvent.OrderItemEvent> items;
  public static NotificationEvent fromOrderEvent(OrderEvent orderEvent) {
    return new NotificationEvent(
            orderEvent.getOrderId(),
            orderEvent.getCustomerName(),
            orderEvent.getContactEmail(),
            orderEvent.getUserId(),
            orderEvent.getShippingAddress(),
            orderEvent.getTotalCost(),
            orderEvent.getItems()
    );
  }
}
