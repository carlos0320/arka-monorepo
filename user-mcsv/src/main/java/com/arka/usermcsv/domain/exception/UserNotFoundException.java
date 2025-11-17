package com.arka.usermcsv.domain.exception;

public class UserNotFoundException extends  DomainException{
  public UserNotFoundException() {
    super("USER_NOT_FOUND", "User does not exists");
  }
}
