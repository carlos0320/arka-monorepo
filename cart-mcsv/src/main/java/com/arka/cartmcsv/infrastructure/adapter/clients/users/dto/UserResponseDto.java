package com.arka.cartmcsv.infrastructure.adapter.clients.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

  private UserData data;

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UserData{
    private Long userId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private ClientDto client;
  }

  @Data
  @NoArgsConstructor
  public static class ClientDto{
    private Long clientId;
    private String customerName;
    private String customerAddress;
  }
}
