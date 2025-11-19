package com.arka.cartmcsv.domain.exceptions;


public class CartItemAlreadyExistsException extends DomainException {
  public CartItemAlreadyExistsException() {
    super("Cart item already exists.");
  }
}
