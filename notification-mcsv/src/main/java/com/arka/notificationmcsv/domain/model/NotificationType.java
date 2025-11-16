package com.arka.notificationmcsv.domain.model;

public enum NotificationType {
  CREATED("Created"),
  CONFIRMED("Confirmed"),
  SHIPPED("Shipped"),
  DELIVERED("Delivered");

  private String value;

  NotificationType(String value){
    this.value = value;
  }

  public String getValue(){
    return this.value;
  }
}
