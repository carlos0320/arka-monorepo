package com.arka.inventorymcsv.infrastructure.adapters.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventDto {
  private Long orderId;
  private List<CartEventDto.CartItem> items;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CartItem{
    private Long productId;
    private Integer quantity;
  }
}
