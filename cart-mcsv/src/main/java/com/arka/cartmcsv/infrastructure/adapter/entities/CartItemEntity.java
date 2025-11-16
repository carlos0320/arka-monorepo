package com.arka.cartmcsv.infrastructure.adapter.entities;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.Product;
import jakarta.persistence.*;
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
@Entity
@Table(schema="shopping", name="cart_item")
public class CartItemEntity {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long cartItemId;

  private Integer quantity;

  private BigDecimal unitPrice;

  private BigDecimal totalCost;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = true)
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private CartEntity cart;

  private Long productId;

  private String productName;
  private String productImage;
}
