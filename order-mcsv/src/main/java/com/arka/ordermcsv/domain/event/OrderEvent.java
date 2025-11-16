package com.arka.ordermcsv.domain.event;

import com.arka.ordermcsv.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
  private Long orderId;
  private String status;
  private Long userId;
  private String username;
  private String contactEmail;
  private String contactPhone;
  private String shippingAddress;
  private String customerName;
  private BigDecimal totalCost;
  private List<OrderItemEvent> items;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderItemEvent {
    private Long productId;
    private Integer quantity;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
  }

  public static OrderEvent fromOrder(Order order){
    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setOrderId(order.getOrderId());
    orderEvent.setStatus(order.getStatus().getValue());
    orderEvent.setCustomerName(order.getCustomerName());
    orderEvent.setContactEmail(order.getContactEmail());
    orderEvent.setContactPhone(order.getContactPhone());
    orderEvent.setShippingAddress(order.getShippingAddress());
    orderEvent.setTotalCost(order.getTotalPrice());
    orderEvent.setUsername(order.getUserName());
    orderEvent.setUserId(order.getUserId());

    List<OrderItemEvent> items = new ArrayList<>();

     order.getItems().forEach(orderItem -> {
      OrderItemEvent orderItemEvent = new OrderItemEvent();
      orderItemEvent.setProductId(orderItem.getProductId());
      orderItemEvent.setQuantity(orderItem.getQuantity());
      orderItemEvent.setProductName(orderItem.getProductName());
      orderItemEvent.setUnitPrice(orderItem.getUnitPrice());
      orderItemEvent.setTotalPrice(orderItem.getTotalPrice());
      items.add(orderItemEvent);
    });

     orderEvent.setItems(items);
     return orderEvent;
  }

}
