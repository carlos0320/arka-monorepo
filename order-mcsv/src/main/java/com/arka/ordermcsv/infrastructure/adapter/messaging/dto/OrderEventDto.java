package com.arka.ordermcsv.infrastructure.adapter.messaging.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderEventDto {
  private Long cartId;
  private List<OrderItemEventDto> items;
  private UserData userData;
  private LocalDateTime createdAt;
  private BigDecimal totalPrice;

  @Data
  @NoArgsConstructor
  public static class OrderItemEventDto {
    private Long productId;
    private BigDecimal price;
    private Integer quantity;
    private String productName;
  }

  @Data
  @NoArgsConstructor
  public static class UserData {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Client client;

    @Data
    @NoArgsConstructor
    public static class Client {
      private Long clientId;
      private String customerName;
    }
  }
}
