package com.arka.productmicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "products")
public class Brand extends BaseEntity{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long brandId;

   private String name;

   private String logo;

   @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
   List<Product> products;
}
