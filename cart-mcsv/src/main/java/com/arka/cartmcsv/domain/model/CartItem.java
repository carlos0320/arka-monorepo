package com.arka.cartmcsv.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItem {
  private Long cartItemId;
  private Integer quantity;

  private BigDecimal unitPrice;
  private BigDecimal totalCost;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Long productId;
  private String productName;
  private String productImage;

  private Cart cart;

  public static CartItem create(Product product, int quantity){
    if (product == null){
      throw new IllegalArgumentException("Product is null");
    }

    if (quantity <= 0){
      throw new IllegalArgumentException("Invalid quantity ");
    }

    CartItem cartItem = new CartItem();
    cartItem.setQuantity(quantity);
    cartItem.setProductId(product.getProductId());
    cartItem.setUnitPrice(product.getPrice());
    cartItem.setProductName(product.getName());
    cartItem.setProductImage(product.getImageUrl());
    cartItem.calculateTotalCost();
    cartItem.setCreatedAt(LocalDateTime.now());
    return cartItem;
  }

  public void increaseQuantity(int increment){
    if (increment <= 0){
      throw new IllegalArgumentException("Invalid increment, must be creater than 0");
    }
    quantity += increment;
    updatedAt = LocalDateTime.now();
    calculateTotalCost();
  }

  public void decreaseQuantity(int decrement){
    if (decrement < 0){
      throw new IllegalArgumentException("Invalid decrement, must be greater than 0");
    }

    quantity -= decrement;
    updatedAt = LocalDateTime.now();
    calculateTotalCost();
  }

  public void calculateTotalCost() {
    totalCost = unitPrice.multiply(BigDecimal.valueOf(quantity));
  }


}
