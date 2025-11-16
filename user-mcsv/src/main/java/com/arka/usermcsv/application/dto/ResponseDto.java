package com.arka.usermcsv.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {
  private T data;
  private String message;
  private boolean success;

  public  ResponseDto(T data) {
    this.data = data;
    success = true;
  }

  public ResponseDto(T data, String message, boolean success) {
    this.data = data;
    this.message = message;
  }
}

