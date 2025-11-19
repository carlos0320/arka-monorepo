package com.arka.apigateway.exception.dto;

import lombok.Data;

@Data
public class ErrorDto {
  private Integer status;
  private String error;
  private String message;
  private String path;
}
