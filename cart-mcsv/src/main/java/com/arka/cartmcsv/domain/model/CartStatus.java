package com.arka.cartmcsv.domain.model;

public enum CartStatus {
  PENDING("pending"),
  CANCELLED("cancelled"),
  CONFIRMED("confirmed"),
  ABANDONED("abandoned");

  private final String value;

  CartStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

