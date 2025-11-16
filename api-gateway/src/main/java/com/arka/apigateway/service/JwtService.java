package com.arka.apigateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.access-expiration}")
  private Long accessExpiration;

  public Claims extractClaims(String token) {
    return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public String extractUserName(String token) {
    return extractClaims(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().getTime() < System.currentTimeMillis();
  }

  public boolean isTokenValid(String token) {
    try {
      extractClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUserId(String token){
    Object value = extractClaims(token).get("userId");
    return value != null ? value.toString() : null;
  }

  public Set<SimpleGrantedAuthority> getAuthorities(String token) {
    Claims claims = extractClaims(token);

    List<String> roles = claims.get("roles", List.class);
    if (roles == null) {
      return Collections.emptySet();
    }

    return roles.stream()
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
  }
}
