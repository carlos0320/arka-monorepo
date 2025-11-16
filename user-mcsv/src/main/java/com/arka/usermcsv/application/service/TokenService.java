package com.arka.usermcsv.application.service;

import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.infrastructure.security.JwtService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TokenService {
  private final JwtService jwtService;

  public TokenService(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public String generateAccessToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getUserId());
    claims.put("roles", user.getRoles() != null
            ? user.getRoles().stream().map(r -> r.getRoleType().name()).collect(Collectors.toList())
            : null);
    return jwtService.generateAccessToken(claims, user.getEmail());
  }

  public Instant getExpirationFromToken(String token) {
    return jwtService.getExpirationFromToken(token);
  }

  public String generateRefreshToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getUserId());
    return jwtService.generateRefreshToken(claims, user.getEmail());
  }
}
