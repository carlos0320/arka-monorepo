package com.arka.apigateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class BearerTokenServerAuthenticationConverter implements ServerAuthenticationConverter {
  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {

    String path = exchange.getRequest().getPath().value();

    //skip public endpoints
    if (path.startsWith("/api/auth/")
            || path.startsWith("/swagger-ui")
            || path.startsWith("/actuator/health/**")
            || path.startsWith("/actuator/info**")
    ) {
      return Mono.empty();
    }

    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return Mono.empty();
    }

    String token = authHeader.substring(7);
    // we pass the token as "credentials"
    return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
  }
}
