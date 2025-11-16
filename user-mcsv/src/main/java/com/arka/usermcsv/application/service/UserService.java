package com.arka.usermcsv.application.service;

import com.arka.usermcsv.application.dto.UserRequestDto;
import com.arka.usermcsv.application.dto.UserResponseDto;
import com.arka.usermcsv.application.mapper.ClientMapper;
import com.arka.usermcsv.application.mapper.SupplierMapper;
import com.arka.usermcsv.application.mapper.UserMapper;
import com.arka.usermcsv.domain.model.*;
import com.arka.usermcsv.domain.model.gateway.RefreshTokenGateway;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import com.arka.usermcsv.domain.usecase.CreateUserUseCase;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserService {
  private final CreateUserUseCase createUserUseCase;
  private final PasswordEncoder passwordEncoder;
  private final RefreshTokenGateway refreshTokenGateway;
  private final UserGateway userGateway;

  public UserService(CreateUserUseCase createUserUseCase, PasswordEncoder passwordEncoder, RefreshTokenGateway refreshTokenGateway, UserGateway userGateway) {
    this.createUserUseCase = createUserUseCase;
    this.passwordEncoder = passwordEncoder;
    this.refreshTokenGateway = refreshTokenGateway;
    this.userGateway = userGateway;
  }

  public UserResponseDto register(UserRequestDto request) {
    String hashed = passwordEncoder.encode(request.getPassword());

    User user = UserMapper.toDomain(request, hashed);
    Client client = ClientMapper.toDomain(request.getClient());
    Supplier supplier = SupplierMapper.toDomain(request.getSupplier());
    Set<RoleTypes> roles = request.getRoles().stream()
            .map(r -> RoleTypes.valueOf(r.toUpperCase()))
            .collect(Collectors.toSet());

    User savedUser = createUserUseCase.execute(user, roles, client, supplier);
    return UserMapper.toDto(savedUser);
  }

  public UserResponseDto getDetails(Long id){
    User userSaved = userGateway.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return UserMapper.toDto(userSaved);
  }

  public User findByRefreshToken(String refreshToken) {
    RefreshToken tokenStored = refreshTokenGateway.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

    Long userId = tokenStored.getUserId();
    return userGateway.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

  }
}
