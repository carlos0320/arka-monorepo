package com.arka.ordermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  private Long orderItemId;
  private Long productId;
  private Integer quantity;
  private BigDecimal unitPrice;
  private String productName;
  private BigDecimal totalPrice;

  @ToString.Exclude
  private Order order;
}
