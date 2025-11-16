package com.arka.usermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Long userId;
  private String password;
  private String address;
  private String name;
  private String email;
  private String phone;

  private boolean isActive = true;

  private Client client;
  private Supplier supplier;

  private Set<Role> roles;
}
