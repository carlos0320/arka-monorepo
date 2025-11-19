package com.arka.ordermcsv.domain.exception;

public class OrderItemsNotFoundException extends ValidationException{
  public OrderItemsNotFoundException(){
    super("Order items is required and must be valid.");
  }
}
