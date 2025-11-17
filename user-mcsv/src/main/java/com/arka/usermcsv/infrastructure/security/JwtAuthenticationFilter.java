package com.arka.usermcsv.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  // intercepta todas las peticiones antes de llegar al controller
  private final JwtService jwtService;
  private final JpaUserDetailService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    // revisamos los headers
    final String authorizationHeader = request.getHeader("Authorization");

    if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
      // si vamos a registrar un usuario o rutas publicas
      filterChain.doFilter(request, response);
      return;
    }

    // extraemos el jwt
    final String jwt = authorizationHeader.substring(7);

    // extraemos el usuario
    final String username = jwtService.extractUserName(jwt);

    // autenticamos el usuario en el contexto de spring
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Carga al usuario desde BD
      if (jwtService.isTokenValid(jwt,userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        // Crea una Authentication en Spring Security
        //Spring, esta request pertenece a este usuario y tiene estos roles”
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // informacion util para auditoria, logs, ips
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }
}
