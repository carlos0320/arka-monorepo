package com.arka.cartmcsv.infrastructure.adapters.entities;

import com.arka.cartmcsv.domain.model.CartStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "shopping_cart", schema = "shopping")
public class CartEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long shoppingCartId;

  private Long userId;
  private CartStatus status;
  private BigDecimal totalCost;

  @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItemEntity> cartItems = new ArrayList<>();
}