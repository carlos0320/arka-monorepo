package com.arka.ordermcsv.infrastructure.adapter.entities;

import com.arka.ordermcsv.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "orders", name = "order")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  private Long userId;

  private String shippingAddress;
  private String contactPhone;
  private String userName;
  private String contactEmail;

  private Long clientId;
  private String customerName;

  private String status;
  private BigDecimal totalPrice;
  private LocalDateTime createdAt;
  private LocalDateTime confirmedAt;
  private LocalDateTime cancelledAt;
  private LocalDateTime shippedAt;
  private LocalDateTime deliveredAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> orderItems;
}
