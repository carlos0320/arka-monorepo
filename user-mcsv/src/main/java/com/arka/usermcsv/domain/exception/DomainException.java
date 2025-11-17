package com.arka.usermcsv.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public abstract class DomainException extends RuntimeException{

  private final String code;

  public DomainException(String code, String message) {
    super(message);
    this.code = code;
  }
}
