package com.arka.ordermcsv.domain.exception;

public class UserIdInvalidException extends ValidationException{
  public UserIdInvalidException(){
    super("User id must be valid and not null");
  }
}
