package com.arka.cartmcsv.infrastructure.controller.dto;

import lombok.Data;

@Data
public class UserDataDto {
  private Long userId;
  private String userName;
  private String userEmail;
  private String userPhone;
  private String userAddress;

  private ClientDataDto clientData;
}
