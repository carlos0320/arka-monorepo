package com.arka.cartmcsv.domain.model;

import com.arka.cartmcsv.domain.exceptions.InvalidDecrementException;
import com.arka.cartmcsv.domain.exceptions.InvalidIncrementException;
import com.arka.cartmcsv.domain.exceptions.InvalidQuantityException;
import com.arka.cartmcsv.domain.exceptions.ProductNullException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

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

  @ToString.Exclude
  private Cart cart;

  public static CartItem create(Product product, int quantity){
    if (product == null){
     throw new ProductNullException();
    }

    if (quantity <= 0){
      throw new InvalidQuantityException();
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
      throw new InvalidIncrementException();
    }
    quantity += increment;
    updatedAt = LocalDateTime.now();
    calculateTotalCost();
  }

  public void decreaseQuantity(int decrement){
    if (decrement < 0){
      throw new InvalidDecrementException();
    }

    quantity -= decrement;
    updatedAt = LocalDateTime.now();
    calculateTotalCost();
  }

  public void calculateTotalCost() {
    totalCost = unitPrice.multiply(BigDecimal.valueOf(quantity));
  }


}
