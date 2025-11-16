package com.arka.usermcsv.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
  private String name;
  private String email;
  private String password;
  private String address;
  private String phone;
  private Set<String> roles;
  private ClientDto client;
  private SupplierDto supplier;
}
