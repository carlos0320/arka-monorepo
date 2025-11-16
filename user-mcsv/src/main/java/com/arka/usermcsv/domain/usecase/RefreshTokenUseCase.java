package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.model.RefreshToken;
import com.arka.usermcsv.domain.model.gateway.RefreshTokenGateway;

import java.time.Instant;

public class RefreshTokenUseCase {

  private final RefreshTokenGateway refreshTokenGateway;

  public RefreshTokenUseCase(RefreshTokenGateway refreshTokenGateway) {
    this.refreshTokenGateway = refreshTokenGateway;
  }

  public RefreshToken validate(String token) {
    RefreshToken stored = refreshTokenGateway.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

    if (stored.isExpired() || stored.getExpiredAt().isBefore(Instant.now())) {
      throw new IllegalArgumentException("Refresh token expired");
    }

    return stored;
  }
}
