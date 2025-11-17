package com.arka.usermcsv.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {
  @Email(message = "Email is not valid")
  @NotBlank(message = "Email must not be empty or null")
  @NotNull(message = "Email must not be null")
  private String email;

  @NotBlank(message = "Password must not be empty or null")
  @NotNull(message = "Passwor must not be null")
  private String password;
}
