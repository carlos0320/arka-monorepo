package com.arka.cartmcsv.domain.exceptions;

public class InvalidIncrementException extends ValidationException {
  public InvalidIncrementException() {
    super("Increment must be greater than 0.");
  }
}