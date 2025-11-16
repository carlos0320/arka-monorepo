package com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto;

import java.util.List;

public class BatchProductRequest {
  private List<Long> productIds;

  public List<Long> getProductIds() {
    return productIds;
  }

  public void setProductIds(List<Long> productIds) {
    this.productIds = productIds;
  }
}
