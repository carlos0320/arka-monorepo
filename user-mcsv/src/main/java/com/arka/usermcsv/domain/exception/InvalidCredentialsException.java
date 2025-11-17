package com.arka.usermcsv.domain.exception;

public class InvalidCredentialsException extends DomainException{
  public InvalidCredentialsException() {
    super("INVALID_CREDENTIALS", "Email or password is incorrect");
  }
}
