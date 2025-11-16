package com.arka.usermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
  private Long id;
  private String token;
  private boolean isExpired = false;
  private LocalDateTime createdAt;
  private Instant expiredAt;
  private Long userId;
}
