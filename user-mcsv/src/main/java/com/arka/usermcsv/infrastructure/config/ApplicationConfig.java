package com.arka.usermcsv.infrastructure.config;

import com.arka.usermcsv.domain.model.gateway.*;
import com.arka.usermcsv.domain.usecase.AuthenticateUserUseCase;
import com.arka.usermcsv.domain.usecase.CreateUserUseCase;
import com.arka.usermcsv.domain.usecase.RefreshTokenUseCase;
import com.arka.usermcsv.infrastructure.adapter.*;
import com.arka.usermcsv.infrastructure.mapper.*;
import com.arka.usermcsv.infrastructure.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

  @Bean
  public UserGateway userGateway(UserRepository userRepository, UserMapper userMapper) {
    return new UserGatewayImpl(userRepository, userMapper);
  }

  @Bean
  public RoleGateway roleGateway(RoleRepository roleRepository, RoleMapper roleMapper) {
    return new RoleGatewayImpl(roleRepository, roleMapper);
  }

  @Bean
  public ClientGateway clientGateway(ClientRepository clientRepository, ClientMapper clientMapper) {
    return new ClientGatewayImpl(clientRepository,clientMapper);
  }

  @Bean
  public SupplierGateway supplierGateway(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
    return new SupplierGatewayImpl(supplierRepository,supplierMapper);
  }

  @Bean
  public RefreshTokenGateway refreshTokenGateway(RefreshTokenRepository refreshTokenRepository, RefreshTokenMapper refreshTokenMapper) {
    return new RefreshTokenGatewayImpl(refreshTokenRepository,refreshTokenMapper);
  }

  @Bean
  public CreateUserUseCase createUserUseCase(UserGateway userGateway, RoleGateway roleGateway) {
    return new CreateUserUseCase(userGateway, roleGateway);
  }

  @Bean
  public AuthenticateUserUseCase authenticateUserUseCase(UserGateway userGateway, PasswordEncoder passwordEncoder) {
    return new AuthenticateUserUseCase(userGateway, passwordEncoder);
  }

  @Bean
  public RefreshTokenUseCase refreshTokenUseCase(RefreshTokenGateway refreshTokenGateway) {
    return new RefreshTokenUseCase(refreshTokenGateway);
  }
}