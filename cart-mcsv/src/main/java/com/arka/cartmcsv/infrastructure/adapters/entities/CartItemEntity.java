package com.arka.cartmcsv.infrastructure.adapters.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "shopping_cart_item", schema = "shopping")
public class CartItemEntity extends  BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long shoppingCartItemId;

  private Long productId;
  private int quantity;
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "shopping_cart_id")
  private CartEntity cartEntity;
}