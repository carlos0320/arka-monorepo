package com.arka.usermcsv.domain.exception;

public class RoleNotFoundException extends DomainException{
  public RoleNotFoundException(){
    super("ROLE_NOT_FOUND", "Role does not exists");
  }
}
