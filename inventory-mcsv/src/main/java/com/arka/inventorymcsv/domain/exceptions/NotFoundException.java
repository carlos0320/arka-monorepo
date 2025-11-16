package com.arka.inventorymcsv.domain.exceptions;

public class NotFoundException extends RuntimeException{
  public NotFoundException(String message){
    super(message);
  }
}
