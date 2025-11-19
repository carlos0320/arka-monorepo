package com.arka.cartmcsv.domain.exceptions;

public class InvalidCartItemIdExceptions extends ValidationException{
  public InvalidCartItemIdExceptions(){
    super("Cart Item id must be greater than 0 and must be valid");
  }
}
