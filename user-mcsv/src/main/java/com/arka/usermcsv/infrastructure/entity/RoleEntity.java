package com.arka.usermcsv.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "users", name = "role")
public class RoleEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private Long roleId;

  @Column(name = "role_type", nullable = false, unique = true)
  private String roleType;
}
