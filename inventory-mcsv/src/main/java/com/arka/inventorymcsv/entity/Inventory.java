package com.arka.inventorymcsv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory", schema = "inventory")
public class Inventory extends  BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long inventoryId;

   private Integer stock;

   private Integer minStock;

   private Integer reservedStock;

   @Column(name = "available_stock", insertable = false, updatable = false)
   private Integer availableStock;

   private Long productId;
}
