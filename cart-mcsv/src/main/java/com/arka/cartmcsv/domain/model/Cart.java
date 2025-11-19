package com.arka.cartmcsv.domain.model;

import com.arka.cartmcsv.domain.exceptions.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Cart {
  private Long cartId;

  private User user;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private String status = CartStatus.PENDING.getValue();

  private BigDecimal totalCost;

  @ToString.Exclude
  private List<CartItem> items = new ArrayList<>();

  public void addNewItem(Product product, Integer quantity) {
    if (!status.equalsIgnoreCase(CartStatus.PENDING.getValue()) && !status.equalsIgnoreCase(CartStatus.ABANDONED.getValue())) {
      throw new CartNotFoundException();
    }

    Optional<CartItem> existingItem = items.stream()
            .filter(i -> i.getProductId().equals(product.getProductId()))
            .findFirst();

    if (existingItem.isPresent()){
      throw new CartItemAlreadyExistsException();
    }

    CartItem cartItem = CartItem.create(product, quantity);
    items.add(cartItem);
    calculateTotalCost();
  }

  public void updateCartItem(CartItem cartItem, int quantity){

    int deltaQuantity = Math.abs(quantity - cartItem.getQuantity());
    Product product;

    if (cartItem.getQuantity() < quantity) {
      cartItem.increaseQuantity(deltaQuantity);
    } else {
      cartItem.decreaseQuantity(deltaQuantity);
    }
    cartItem.setUpdatedAt(LocalDateTime.now());
    cartItem.calculateTotalCost();
    calculateTotalCost();
  }


  public void removeCartItem(Long cartItemId){
    items = items.stream()
            .filter(item -> !item.getCartItemId().equals(cartItemId))
            .collect(Collectors.toList());

    calculateTotalCost();
    updatedAt = LocalDateTime.now();
  }

  public boolean isPending() {
    return CartStatus.PENDING.getValue().equals(this.status);
  }
  public boolean isAbandoned() { return CartStatus.ABANDONED.getValue().equals(this.status); }

  public CartItem findCartItemByProductId(Long productId){
    return items
            .stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ProductNotInCartException(productId));
  }

  public CartItem findCartItemById(Long cartItemId){
    return items
            .stream()
            .filter(item -> item.getCartItemId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new CartItemNotFoundException(cartItemId));
  }


  public void calculateTotalCost() {
    totalCost = items.stream()
            .map(CartItem::getTotalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void cancel(){
    if (!status.equalsIgnoreCase(CartStatus.PENDING.getValue()) && !status.equalsIgnoreCase(CartStatus.ABANDONED.getValue())) {
      throw new InvalidCartStatusTransitionException("Cant cancel a cart that is already confirmed or cancelled");
    }
    status = CartStatus.CANCELLED.getValue().toLowerCase();
    updatedAt = LocalDateTime.now();
  }

  public void confirm(){
    if (!status.equalsIgnoreCase(CartStatus.PENDING.getValue()) && !status.equalsIgnoreCase(CartStatus.ABANDONED.getValue())) {
      throw new InvalidCartStatusTransitionException("Cant cancel a cart that is already confirmed or cancelled");
    }
    status = CartStatus.CONFIRMED.getValue().toLowerCase();
    updatedAt = LocalDateTime.now();
  }

}
