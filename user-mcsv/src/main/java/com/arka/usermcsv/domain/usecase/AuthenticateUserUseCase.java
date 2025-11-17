package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.exception.InvalidCredentialsException;
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

    if (email == null || rawPassword == null) {
      throw new InvalidCredentialsException();
    }

    Optional<User> userStored = userGateway.findByEmail(email);
    if (userStored.isEmpty()) {
      throw new InvalidCredentialsException();
    }

    User user = userStored.get();
    if (!encoder.matches(rawPassword, user.getPassword())) {
      throw new InvalidCredentialsException();
    }

    return new AuthenticationResult(user);
  }
}
