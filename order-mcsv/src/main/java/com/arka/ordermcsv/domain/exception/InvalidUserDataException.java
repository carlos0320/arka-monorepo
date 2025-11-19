package com.arka.ordermcsv.domain.exception;

public class InvalidUserDataException extends ValidationException{
  public InvalidUserDataException(String message){
    super(message);
  }
}
