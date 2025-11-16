package com.arka.usermcsv.domain.model;

public enum RoleTypes {
  ADMIN("admin"),
  CLIENT("client"),
  SUPPLIER("supplier");

  private final String value;

  RoleTypes(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
