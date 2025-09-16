package com.arka.productmicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "product", schema = "products")
public class Product extends BaseEntity{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "product_id")
   private Long productId;

   private String name;

   private String imageUrl;

   private String description;

   private BigDecimal price;

   private boolean isActive = true;

   @ManyToOne
   @JoinColumn(name="category_id")
   private Category category;

   @ManyToOne
   @JoinColumn(name="brand_id")
   private Brand brand;
}
