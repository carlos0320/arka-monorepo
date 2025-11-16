package com.arka.ordermcsv.infrastructure.adapter.messaging.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemEventDto {
  private Long productId;
  private BigDecimal price;
  private Integer quantity;
  private String productName;
}
