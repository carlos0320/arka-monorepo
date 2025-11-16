package com.arka.usermcsv.application.service;

import com.arka.usermcsv.application.dto.AuthRequestDto;
import com.arka.usermcsv.application.dto.AuthResponseDto;
import com.arka.usermcsv.application.mapper.UserMapper;
import com.arka.usermcsv.domain.model.AuthenticationResult;
import com.arka.usermcsv.domain.model.RefreshToken;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.domain.model.gateway.RefreshTokenGateway;
import com.arka.usermcsv.domain.usecase.AuthenticateUserUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class AuthService {
  private final AuthenticateUserUseCase authenticateUserUseCase;
  private final RefreshTokenGateway refreshTokenGateway;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public AuthService(AuthenticateUserUseCase authenticateUserUseCase, RefreshTokenGateway refreshTokenGateway,
                     PasswordEncoder passwordEncoder,
                     TokenService tokenService) {
    this.authenticateUserUseCase = authenticateUserUseCase;
    this.refreshTokenGateway = refreshTokenGateway;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  public AuthResponseDto login(String email, String password) {
    AuthenticationResult result = authenticateUserUseCase.execute(email, password);

    User user = result.getUser();
    String accessToken = tokenService.generateAccessToken(user);
    String refreshTokenString = tokenService.generateRefreshToken(user);

    Instant expirationInstant = tokenService.getExpirationFromToken(refreshTokenString);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(refreshTokenString);
    refreshToken.setExpired(false);
    refreshToken.setCreatedAt(LocalDateTime.now());
    refreshToken.setExpiredAt(expirationInstant);
    refreshToken.setUserId(user.getUserId());

    refreshTokenGateway.save(refreshToken);


    return new AuthResponseDto(accessToken, refreshTokenString, UserMapper.toDto(user));
  }
}
