package com.arka.cartmcsv.domain.exceptions;

public class InvalidQuantityException extends ValidationException {
  public InvalidQuantityException() {
    super("Quantity must be greater than 0.");
  }
}