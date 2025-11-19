package com.arka.cartmcsv.domain.exceptions;

public class ProductNotInCartException extends DomainException {
  public ProductNotInCartException(Long productId) {
    super("Product with id " + productId + " is not in the cart.");
  }
}