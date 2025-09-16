package com.arka.productmicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
   private String message;
   private String statusCode;
}
