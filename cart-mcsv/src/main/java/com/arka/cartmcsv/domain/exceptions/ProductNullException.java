package com.arka.cartmcsv.domain.exceptions;

public class ProductNullException extends ValidationException {
  public ProductNullException() {
    super("Product cannot be null.");
  }
}