package com.arka.cartmcsv.domain.exceptions;

public class InvalidDecrementException extends ValidationException {
  public InvalidDecrementException() {
    super("Decrement must be greater than or equal to 0.");
  }
}