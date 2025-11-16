package com.arka.cartmcsv.domain.model;

import jakarta.persistence.Id;
import lombok.Data;

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

  private List<CartItem> items = new ArrayList<>();

  public void addNewItem(Product product, Integer quantity) {
    if (!isPending()) {
      throw new RuntimeException("Cart must be pending to add a new item");
    }

    Optional<CartItem> existingItem = items.stream()
            .filter(i -> i.getProductId().equals(product.getProductId()))
            .findFirst();

    if (existingItem.isPresent()){
      throw new RuntimeException("Cart item already exists");
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

  public CartItem findCartItemByProductId(Long productId){
    return items
            .stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("item with that product not found"));
  }

  public CartItem findCartItemById(Long cartItemId){
    return items
            .stream()
            .filter(item -> item.getCartItemId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("item with that id not found"));
  }


  public void calculateTotalCost() {
    totalCost = items.stream()
            .map(CartItem::getTotalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void cancel(){
    if (!status.equalsIgnoreCase(CartStatus.PENDING.getValue())) {
      throw new RuntimeException("Cant cancel a cart that is already confirmed or cancelled");
    }
    status = CartStatus.CANCELLED.getValue().toLowerCase();
    updatedAt = LocalDateTime.now();
  }

  public void confirm(){
    if (!status.equalsIgnoreCase(CartStatus.PENDING.getValue())) {
      throw new RuntimeException("Cant confirm a cart that is already confirmed or cancelled");
    }
    status = CartStatus.CONFIRMED.getValue().toLowerCase();
    updatedAt = LocalDateTime.now();
  }

}
