package com.arka.cartmcsv.domain.exceptions;

public class UserNotFoundException extends DomainException {
  public UserNotFoundException(Long id) {
    super("User not found: " + id);
  }
}