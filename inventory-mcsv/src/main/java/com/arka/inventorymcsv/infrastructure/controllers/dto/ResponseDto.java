package com.arka.inventorymcsv.infrastructure.controllers.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ResponseDto<T> {
  private T data;
  private HttpStatus status;
}
