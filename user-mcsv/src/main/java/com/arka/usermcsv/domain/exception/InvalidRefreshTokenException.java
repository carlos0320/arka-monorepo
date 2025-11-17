package com.arka.usermcsv.domain.exception;

public class InvalidRefreshTokenException extends DomainException{
  public InvalidRefreshTokenException(){
    super("INVALID_REFRESH_TOKEN", "Refresh token is not valid.");
  }
}
