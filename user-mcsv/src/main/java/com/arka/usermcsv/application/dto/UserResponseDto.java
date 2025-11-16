package com.arka.usermcsv.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long userId;
  private String name;
  private String email;
  private String address;
  private String phone;
  private boolean isActive;
  private Set<String> roles;
  private ClientDto client;
  private SupplierDto supplier;
}
