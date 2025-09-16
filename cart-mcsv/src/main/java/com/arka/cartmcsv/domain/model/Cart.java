package com.arka.cartmcsv.domain.model;

import com.arka.cartmcsv.domain.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends  BaseEntity {
  private Long shoppingCartId;

  private Long userId;
  private CartStatus status;
  private BigDecimal totalCost;

  private List<CartItem> cartItems = new ArrayList<>();
}
