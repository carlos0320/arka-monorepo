package com.arka.cartmcsv.infrastructure.controller.dto;

import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.infrastructure.adapter.clients.users.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
  private Long cartId;
  private LocalDateTime createdAt;
  private BigDecimal totalCost;

  private UserDataDto userData;
  private List<CartItemDto> items;
}
