package com.arka.ordermcsv.domain.exception;

public class NoPendingOrderFoundException extends  DomainException {
  public NoPendingOrderFoundException(){
    super("No pending order found");
  }
}
