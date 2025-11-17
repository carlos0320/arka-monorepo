package com.arka.usermcsv.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
  @NotNull(message = "Role must not be null")
  @NotBlank(message = "Role must not be empty")
  private String roleType;
}
