package com.arka.usermcsv.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Representa el resultado de una autenticacion exitosa
// la capa de aplicacion la puede usar para generar el jwt
@Data
@AllArgsConstructor
public class AuthenticationResult {
  private final User user;
}
