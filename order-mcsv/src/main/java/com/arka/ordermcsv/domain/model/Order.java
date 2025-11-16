package com.arka.ordermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private Long orderId;
  private Long userId;

  private String userName;
  private String contactEmail;
  private String contactPhone;

  private String shippingAddress;

  private Long clientId;
  private String customerName;

  private OrderStatus status;
  private BigDecimal totalPrice;

  private LocalDateTime createdAt;
  private LocalDateTime confirmedAt;
  private LocalDateTime cancelledAt;
  private LocalDateTime shippedAt;
  private LocalDateTime deliveredAt;

  List<OrderItem> items;

  public static Order createOrder(
          Long userId,
          String userName,
          String userEmail,
          String contactPhone,
          String shippingAddress,
          Long clientId,
          String customerName
  ){
    Order order = new Order();
    order.setItems(new ArrayList<>());
    order.setUserName(userName);
    order.setContactEmail(userEmail);
    order.setContactPhone(contactPhone);
    order.setShippingAddress(shippingAddress);
    order.setClientId(clientId);
    order.setCustomerName(customerName);
    order.setStatus(OrderStatus.PENDING);
    order.setUserId(userId);
    order.setTotalPrice(BigDecimal.ZERO);
    order.setCreatedAt(LocalDateTime.now());
    return order;
  }

  public void addOrderItem(OrderItem item){
    this.items.add(item);
  }

  public void calculateCost(){
    totalPrice = items
            .stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO,BigDecimal::add);

  }
}
