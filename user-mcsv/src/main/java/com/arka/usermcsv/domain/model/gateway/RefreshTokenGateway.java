package com.arka.usermcsv.domain.model.gateway;

import com.arka.usermcsv.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenGateway {
  RefreshToken save(RefreshToken token);
  Optional<RefreshToken> findByToken(String token);
  void delete(RefreshToken token);
  Optional<RefreshToken> findByUserId(Long userId);
}
