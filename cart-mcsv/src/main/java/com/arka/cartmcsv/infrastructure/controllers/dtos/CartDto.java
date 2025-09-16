package com.arka.cartmcsv.infrastructure.controllers.dtos;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
  private Long shoppingCartId;

  private Long userId;
  private BigDecimal totalCost;

  private List<CartItemDto> cartItems;
}
