package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.model.AuthenticationResult;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.function.BiPredicate;

public class AuthenticateUserUseCase {
  private final UserGateway userGateway;
  PasswordEncoder encoder = new BCryptPasswordEncoder();


  public AuthenticateUserUseCase(UserGateway userGateway, PasswordEncoder encoder) {
    this.userGateway = userGateway;
    this.encoder = encoder;
  }

  public AuthenticationResult execute(String email, String rawPassword) {
    Optional<User> userOpt = userGateway.findByEmail(email);
    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    User user = userOpt.get();
    if (!encoder.matches(rawPassword, user.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    // Domain just validates and returns a signal — tokens are built in app layer
    return new AuthenticationResult(user);
  }
}
