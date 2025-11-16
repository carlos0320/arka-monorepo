package com.arka.cartmcsv.domain.model;

import lombok.Data;

@Data
public class User {
  private Long userId;
  private String name;
  private String phone;
  private String email;
  private String address;

  private Client client;
}
