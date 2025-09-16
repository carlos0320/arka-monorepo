package com.arka.usermcsv.entities;

public enum RoleTypes {
   ADMIN, CLIENT, SUPPLIER;

   public static RoleTypes fromString(String value) {
      if (value == null) {
         throw new IllegalArgumentException("Role type cannot be null");
      }
      try {
         return RoleTypes.valueOf(value.toUpperCase());
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("Invalid role type: " + value);
      }
   }
}
