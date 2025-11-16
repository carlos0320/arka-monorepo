package com.arka.usermcsv.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "users", name = "client")
public class ClientEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long clientId;

  private String customerName;

  private String customerAddress;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;
}
