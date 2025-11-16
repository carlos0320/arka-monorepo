package com.arka.cartmcsv.infrastructure.adapter.entities;


public enum CartEntityStatus {
  PENDING("pending"),
  CANCELLED("cancelled"),
  CONFIRMED("confirmed"),
  ABANDONED("abandoned");

  private final String value;

  CartEntityStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
