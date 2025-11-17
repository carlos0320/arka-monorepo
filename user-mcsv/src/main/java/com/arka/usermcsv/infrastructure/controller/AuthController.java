package com.arka.usermcsv.infrastructure.controller;

import com.arka.usermcsv.application.dto.*;
import com.arka.usermcsv.application.service.AuthService;
import com.arka.usermcsv.application.service.TokenService;
import com.arka.usermcsv.application.service.UserService;
import com.arka.usermcsv.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final TokenService tokenService;
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto<UserResponseDto>> register(@Valid @RequestBody UserRequestDto request) {
    return ResponseEntity.ok(new ResponseDto<>(userService.register(request)));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<AuthResponseDto>> login(@Valid @RequestBody AuthRequestDto request) {
    AuthResponseDto response = authService.login(request.getEmail(), request.getPassword());
    return ResponseEntity.ok(new ResponseDto<>(response));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseDto<TokenResponseDto>> refresh(@Valid @RequestBody RefreshTokenRequestDto request) {

    User user = userService.findByRefreshToken(request.getRefreshToken());
    String newAccessToken = tokenService.generateAccessToken(user);
    String newRefreshToken = tokenService.generateRefreshToken(user);

    TokenResponseDto tokens = new TokenResponseDto(newAccessToken, newRefreshToken);
    return ResponseEntity.ok(new ResponseDto<>(tokens));
  }
}