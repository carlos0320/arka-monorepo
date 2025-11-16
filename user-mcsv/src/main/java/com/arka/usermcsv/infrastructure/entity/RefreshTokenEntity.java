package com.arka.usermcsv.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(schema = "users", name="refresh_token")
@Data
public class RefreshTokenEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  private boolean isExpired = false;

  private LocalDateTime createdAt;

  private Instant expiredAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private UserEntity user;
}
