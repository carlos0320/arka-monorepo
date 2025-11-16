package com.arka.apigateway.security;

import com.arka.apigateway.service.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtService jwtService;

  public JwtAuthenticationManager(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();

    if (!jwtService.isTokenValid(token)) {
      return Mono.error(new BadCredentialsException("Invalid Jwt"));
    }

    String username = jwtService.extractUserName(token);
    Set<SimpleGrantedAuthority> authorities = jwtService.getAuthorities(token);

    return Mono.just(new UsernamePasswordAuthenticationToken(username,null, authorities));

  }
}
