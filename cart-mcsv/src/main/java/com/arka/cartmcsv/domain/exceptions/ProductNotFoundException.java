package com.arka.cartmcsv.domain.exceptions;

import java.util.List;

public class ProductNotFoundException extends DomainException {
  public ProductNotFoundException(List<Long> ids) {
    super("Some products were not found: " + ids);
  }
}
