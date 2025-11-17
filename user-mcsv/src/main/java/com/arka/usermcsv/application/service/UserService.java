package com.arka.usermcsv.application.service;

import com.arka.usermcsv.application.dto.UserRequestDto;
import com.arka.usermcsv.application.dto.UserResponseDto;
import com.arka.usermcsv.application.exception.ValidationException;
import com.arka.usermcsv.application.mapper.ClientMapper;
import com.arka.usermcsv.application.mapper.SupplierMapper;
import com.arka.usermcsv.application.mapper.UserMapper;
import com.arka.usermcsv.domain.exception.InvalidRefreshTokenException;
import com.arka.usermcsv.domain.exception.UserNotFoundException;
import com.arka.usermcsv.domain.model.*;
import com.arka.usermcsv.domain.model.gateway.RefreshTokenGateway;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import com.arka.usermcsv.domain.usecase.CreateUserUseCase;
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

    if (roles.contains(RoleTypes.CLIENT) && client == null) {
      throw new ValidationException("You must provide client data for the role client");
    }

    if (client != null && !roles.contains(RoleTypes.CLIENT)){
      throw new ValidationException("You must set the role client for the client information");
    }

    User savedUser = createUserUseCase.execute(user, roles, client, supplier);
    return UserMapper.toDto(savedUser);
  }

  public UserResponseDto getDetails(Long id){
    if (id == null) {
      throw new ValidationException("User ID must not be null");
    }
    User userSaved = userGateway.findById(id)
            .orElseThrow(() -> new UserNotFoundException());
    return UserMapper.toDto(userSaved);
  }

  public User findByRefreshToken(String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new ValidationException("Refresh token must not be null and must be valid");
    }
    RefreshToken tokenStored = refreshTokenGateway.findByToken(refreshToken)
            .orElseThrow(() -> new InvalidRefreshTokenException());

    Long userId = tokenStored.getUserId();
    return userGateway.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());

  }
}
