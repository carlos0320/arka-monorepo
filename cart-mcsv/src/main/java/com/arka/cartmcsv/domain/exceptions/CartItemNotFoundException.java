package com.arka.cartmcsv.domain.exceptions;

public class CartItemNotFoundException extends DomainException {
  public CartItemNotFoundException(Long id) {
    super("Cart item with id " + id + " not found.");
  }
}