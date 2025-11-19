package com.arka.ordermcsv.domain.exception;

public class NoConfirmedOrderFound extends DomainException{
  public NoConfirmedOrderFound(){
    super("No confirmed order found");
  }
}
