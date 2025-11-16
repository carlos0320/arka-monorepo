package com.arka.usermcsv.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final SecretKey secretKey;
  private final Long accessExpiration;
  private final Long refreshExpiration;

  public JwtService(@Value("${jwt.secret-key}") String secretKey,
                    @Value("${jwt.access-expiration}") Long accessExpiration,
                    @Value("${jwt.refresh-expiration}") Long refreshExpiration) {
    this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS256.getJcaName());
    this.accessExpiration = accessExpiration;
    this.refreshExpiration = refreshExpiration;
  }

  private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

  public Instant getExpirationFromToken(String token) {
    return extractAllClaims(token).getExpiration().toInstant();
  }

  public String generateAccessToken(Map<String, Object> extraClaims, String username) {
    return buildToken(extraClaims, username, accessExpiration);
  }

  public String generateRefreshToken(Map<String, Object> extraClaims, String username) {
    return buildToken(extraClaims, username, refreshExpiration);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public String extractUserName(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  public boolean isTokenValid(String token, String username) {
    final String tokenUsername = extractUserName(token);
    return tokenUsername.equals(username) && !isTokenExpired(token);
  }
}
