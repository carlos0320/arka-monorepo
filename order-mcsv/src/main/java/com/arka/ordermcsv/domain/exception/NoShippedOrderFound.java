package com.arka.ordermcsv.domain.exception;

public class NoShippedOrderFound extends  DomainException{
  public NoShippedOrderFound(){
    super("No shipped order found");
  }
}
