package com.arka.cartmcsv.infrastructure.controller.dto;

import com.arka.cartmcsv.infrastructure.adapter.entities.CartEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
  private Long cartId;
  private Long cartItemId;

  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalCost;

  private LocalDateTime createdAt;

  private String productName;
  private String productImage;
  private Long productId;
}
