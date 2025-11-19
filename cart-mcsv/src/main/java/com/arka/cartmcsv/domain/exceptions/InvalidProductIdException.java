package com.arka.cartmcsv.domain.exceptions;

public class InvalidProductIdException extends ValidationException {
  public InvalidProductIdException() {
    super("Product ID must be a positive number.");
  }
}
