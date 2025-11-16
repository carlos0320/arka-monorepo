package com.arka.usermcsv.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "users", name = "supplier")
public class SupplierEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long supplierId;

  private String companyName;
  private String companyEmail;
  private String companyAddress;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false )
  private UserEntity user;
}
