package com.arka.usermcsv.domain.exception;

public class RefreshTokenExpiredException extends DomainException{
  public RefreshTokenExpiredException() {
    super("REFRESH_TOKEN_EXPIRED", "Refresh token has expired");
  }
}
