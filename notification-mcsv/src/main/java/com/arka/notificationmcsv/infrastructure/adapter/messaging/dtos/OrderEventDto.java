package com.arka.notificationmcsv.infrastructure.messaging.orderDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderEventDto {
  private Long orderId;
  private String customerName;
  private String contactEmail;
  private Long userId;
  private String shippingAddress;
  private BigDecimal totalCost;
  private List<OrderItemEvent> items;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderItemEvent {
    private Integer quantity;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
  }
}
