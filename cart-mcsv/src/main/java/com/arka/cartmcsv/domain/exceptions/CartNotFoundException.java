package com.arka.cartmcsv.domain.exceptions;

public class CartNotFoundException extends DomainException{
  public CartNotFoundException() {
    super("Pending or abandoned cart not found");
  }
}
