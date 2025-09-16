package com.arka.productmicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "products")
public class Category extends BaseEntity{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long categoryId;

   private String name;

   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
   List<Product> products;
}
