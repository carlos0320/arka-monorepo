package com.arka.usermcsv.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequestDto {
  @NotNull(message = "Refresh token must not be null")
  @NotBlank(message = "Refresh token must not be empty")
  private String refreshToken;
}
