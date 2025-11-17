package com.arka.usermcsv.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDto {
  private Long clientId;
  @NotBlank(message = "Customer name must not be empty")
  @NotNull(message = "Customer name must not be null")
  private String customerName;

  @NotBlank(message = "Customer address must not be empty")
  @NotNull(message = "Customer address must not be null")
  private String customerAddress;
}
