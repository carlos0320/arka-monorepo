package com.arka.apigateway.security;

import com.arka.apigateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AddUserHeadersFilter implements GlobalFilter, Ordered {

  private final JwtService jwtService;

  public AddUserHeadersFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (auth == null || !auth.startsWith("Bearer ")){
      return chain.filter(exchange);
    }

    String token = auth.substring(7);
    String userId = jwtService.getUserId(token);
    List<String> roles =  jwtService.extractClaims(token).get("roles", List.class);
    String rolesHeader =roles != null ? String.join(",", roles) : "";

    ServerHttpRequest mutated = exchange.getRequest()
            .mutate()
            .header("X-User-Id", userId != null ? userId : "")
            .header("X-User-Roles", rolesHeader)
            .build();

    return chain.filter(exchange.mutate().request(mutated).build());
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
