package com.arka.usermcsv.infrastructure.adapter;

import com.arka.usermcsv.domain.model.RefreshToken;
import com.arka.usermcsv.domain.model.gateway.RefreshTokenGateway;
import com.arka.usermcsv.infrastructure.entity.RefreshTokenEntity;
import com.arka.usermcsv.infrastructure.mapper.RefreshTokenMapper;
import com.arka.usermcsv.infrastructure.repository.RefreshTokenRepository;

import java.util.Optional;

public class RefreshTokenGatewayImpl implements RefreshTokenGateway {
  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenMapper refreshTokenMapper;

  public RefreshTokenGatewayImpl(RefreshTokenRepository refreshTokenRepository, RefreshTokenMapper refreshTokenMapper) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.refreshTokenMapper = refreshTokenMapper;
  }

  @Override
  public RefreshToken save(RefreshToken token) {
    RefreshTokenEntity entity = refreshTokenMapper.toEntity(token);
    return refreshTokenMapper.toDomain(refreshTokenRepository.save(entity));
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token)
            .map(refreshTokenMapper::toDomain);
  }

  @Override
  public void delete(RefreshToken token) {

  }

  @Override
  public Optional<RefreshToken> findByUserId(Long userId) {
    return Optional.empty();
  }
}
