package com.arka.cartmcsv.infrastructure.controllers.dtos;

import com.arka.cartmcsv.domain.model.Cart;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
  private Long productId;
  private Integer quantity;
  private BigDecimal price;
}
