package com.arka.usermcsv.domain.model;

/**
 * Represents the result of a successful authentication.
 * The application layer can use this to generate JWT tokens.
 */
public class AuthenticationResult {
  private final User user;

  public AuthenticationResult(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
