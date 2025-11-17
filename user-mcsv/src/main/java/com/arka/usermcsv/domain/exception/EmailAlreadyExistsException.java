package com.arka.usermcsv.domain.exception;

public class EmailAlreadyExistsException extends  DomainException {
  public EmailAlreadyExistsException() {
    super("EMAIL_ALREADY_REGISTERED", "This email is already in use");
  }
}
