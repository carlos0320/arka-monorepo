package com.arka.ordermcsv.infrastructure.adapter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "orders", name = "order_items")
public class OrderItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;

  private Long productId;
  private Integer quantity;
  private BigDecimal unitPrice;
  private String productName;
  private BigDecimal totalPrice;

  @ManyToOne()
  @JoinColumn(name="order_id")
  private OrderEntity order;


}
