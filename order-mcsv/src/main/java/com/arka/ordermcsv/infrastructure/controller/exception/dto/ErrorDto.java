package com.arka.ordermcsv.infrastructure.controller.exception.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {
  private final String errorCode;
  private final String message;
  private final LocalDateTime date;
  private final String path;

  public ErrorDto(String errorCode, String message, String path){
    this.errorCode = errorCode;
    this.message = message;
    this.date = LocalDateTime.now();
    this.path = path;
  }
}
