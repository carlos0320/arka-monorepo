package com.arka.inventorymcsv.infrastructure.adapters.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "products", name = "brand")
public class BrandEntity {
  @Id
  private Long brandId;

  @NotNull(message = "Brand name is required")
  @NotBlank(message = "Brand name must not be empty")
  private String name;

  private String logo;
}
