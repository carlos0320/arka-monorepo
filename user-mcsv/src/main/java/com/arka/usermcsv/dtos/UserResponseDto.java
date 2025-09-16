package com.arka.usermcsv.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
   private Long userId;
   private String name;
   private String email;
   private String phone;
   private boolean isActive;
   private ClientDto client;
   private String role;
   private SupplierDto supplier;
}
