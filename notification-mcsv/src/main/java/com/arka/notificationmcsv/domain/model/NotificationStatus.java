package com.arka.notificationmcsv.domain.model;

public enum NotificationStatus {
  DELIVERED("delivered"),
  ERROR("error"),
  PENDING("pending");

  private String value;

  NotificationStatus(String value){
    this.value = value;
  }

  public String getValue(){
    return this.value;
  }

}
