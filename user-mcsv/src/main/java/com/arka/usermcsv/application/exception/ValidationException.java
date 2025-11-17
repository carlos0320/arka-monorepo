package com.arka.usermcsv.application.exception;

public class ValidationException extends RuntimeException{
  public ValidationException(String message) {
    super(message);
  }
}
