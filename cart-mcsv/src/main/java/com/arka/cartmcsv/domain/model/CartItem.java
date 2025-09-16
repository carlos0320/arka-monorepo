package com.arka.cartmcsv.domain.model;

import com.arka.cartmcsv.domain.model.Cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem extends  BaseEntity {
  private Long shoppingCartItemId;

  private Long productId;
  private Integer quantity;
  private BigDecimal price;

  private Cart cart;
}
