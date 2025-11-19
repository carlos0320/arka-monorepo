package com.arka.cartmcsv.infrastructure.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {
  @NotNull(message = "Quantity must not be null")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Long productId;

  @NotNull(message = "Quantity must not be null")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Integer quantity;
}
