package com.arka.usermcsv.infrastructure.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
