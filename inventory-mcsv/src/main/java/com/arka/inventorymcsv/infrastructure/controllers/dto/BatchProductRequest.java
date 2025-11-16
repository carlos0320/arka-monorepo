package com.arka.inventorymcsv.infrastructure.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchProductRequest {
  private List<Long> productIds;
}
