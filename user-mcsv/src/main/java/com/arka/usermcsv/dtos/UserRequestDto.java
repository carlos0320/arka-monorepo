package com.arka.usermcsv.dtos;

import lombok.Data;

@Data
public class UserRequestDto {
   private String name;
   private String email;
   private String password;
   private String phone;
   private ClientDto client;
   private String role;
   private SupplierDto supplier;
}
