package com.arka.cartmcsv.domain.exceptions;

public class InvalidUserIdException extends ValidationException {
  public InvalidUserIdException() {
    super("User ID must be a positive number and mustnot be null.");
  }
}