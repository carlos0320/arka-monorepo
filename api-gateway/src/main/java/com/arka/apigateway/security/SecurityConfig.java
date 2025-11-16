package com.arka.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


  private final JwtAuthenticationManager jwtAuthManager;
  private final BearerTokenServerAuthenticationConverter jwtConverter;

  public SecurityConfig(JwtAuthenticationManager jwtAuthManager,
                        BearerTokenServerAuthenticationConverter jwtConverter) {
    this.jwtAuthManager = jwtAuthManager;
    this.jwtConverter = jwtConverter;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

    AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
    jwtFilter.setServerAuthenticationConverter(jwtConverter);


    return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/actuator/health/**", "/actuator/info").permitAll()
                    .pathMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/inventory/products").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/inventory/products/details/{productId}").permitAll()
                    .pathMatchers("/swagger-ui.html", "/v3/api-docs/**").permitAll()
                    .pathMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyExchange().authenticated()
            )
            // inject our JWT filter BEFORE the normal auth
            .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
  }

}
