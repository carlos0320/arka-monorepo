package com.arka.usermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
  private Long clientId;
  private String customerName;
  private String customerAddress;
  private Long userId;
}
