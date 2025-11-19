package com.arka.cartmcsv.domain.exceptions;

public class InvalidCartStatusTransitionException extends DomainException {
  public InvalidCartStatusTransitionException(String action) {
    super("Cannot " + action + " a cart that is not in pending or abandoned status.");
  }
}