package com.arka.usermcsv.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
  @NotNull(message = "User name must not be null")
  @NotBlank(message = "User name must not be empty")
  @Size(min = 2, message = "User name must be at lest 2 characters")
  private String name;

  @NotNull(message = "User email must not be null")
  @NotBlank(message = "User email must not be empty")
  @Email(message = "User email must be valid")
  private String email;

  @NotNull(message = "User password must not be null")
  @NotBlank(message = "User password must not be empty")
  @Size(min= 6, message = "Password must be at least 6 characters")
  private String password;

  @NotNull(message = "User address must not be null")
  @NotBlank(message = "User address must not be empty")
  @Size(min= 4, message = "User address must be at least 4 characters")
  private String address;


  @NotNull(message = "User phone must not be null")
  @NotBlank(message = "User phone must not be empty")
  @Size(min= 10, message = "User phone must be at least 10 characters")
  private String phone;

  @NotNull(message = "User roles must not be null")
  @NotBlank(message = "User roles must not be empty")
  private Set<String> roles;

  private ClientDto client;
  private SupplierDto supplier;
}
