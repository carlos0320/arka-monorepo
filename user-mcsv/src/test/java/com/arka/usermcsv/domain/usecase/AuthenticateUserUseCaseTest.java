package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.exception.InvalidCredentialsException;
import com.arka.usermcsv.domain.model.AuthenticationResult;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // para poder usar anotaciones como @Mock (Mockito)
public class AuthenticateUserUseCaseTest {

  // mock de las dependencias
  @Mock
  private UserGateway userGateway;

  @Mock
  private PasswordEncoder passwordEncoder;

  private AuthenticateUserUseCase useCase; // caso de uso que vamos a testear


  @BeforeEach
  void setUp() {
    // inyeccion de dependencias
    useCase = new AuthenticateUserUseCase(userGateway, passwordEncoder);
  }

  @Test
  void shouldReturnAuthenticationResult_whenCredentialsAreValid() {
    // Arrange -> fake data
    String email = "test@example.com";
    String rawPassword = "plain-pass";
    String hashedPassword = "$2a$10$hashed";

    User stored = new User();
    stored.setUserId(123L);
    stored.setEmail(email);
    stored.setPassword(hashedPassword);

    when(userGateway.findByEmail(email)).thenReturn(Optional.of(stored)); // simulamos la respuesta de la capa de persistencia
    when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

    // Act
    AuthenticationResult result = useCase.execute(email, rawPassword);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getUser()).isEqualTo(stored); // validamos que el usuario autenticado sea el mismo que el que mockeamos
    verify(userGateway).findByEmail(email); // verifica que se haya llamado a los mocks
    verify(passwordEncoder).matches(rawPassword, hashedPassword);
  }

  @Test
  void shouldThrowInvalidCredentials_whenEmailIsNull() {
    assertThatThrownBy(() -> useCase.execute(null, "any"))
            .isInstanceOf(InvalidCredentialsException.class);
  }

  @Test
  void shouldThrowInvalidCredentials_whenUserNotFound() {
    String email = "noone@example.com";
    when(userGateway.findByEmail(email)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> useCase.execute(email, "some"))
            .isInstanceOf(InvalidCredentialsException.class);

    verify(userGateway).findByEmail(email);
  }

  @Test
  void shouldThrowInvalidCredentials_whenPasswordDoesNotMatch() {
    String email = "test@example.com";
    String rawPassword = "wrong";
    String hashedPassword = "$2a$10$hashed";

    User stored = new User();
    stored.setEmail(email);
    stored.setPassword(hashedPassword);

    when(userGateway.findByEmail(email)).thenReturn(Optional.of(stored));
    when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(false);

    assertThatThrownBy(() -> useCase.execute(email, rawPassword))
            .isInstanceOf(InvalidCredentialsException.class);

    verify(passwordEncoder).matches(rawPassword, hashedPassword);
  }


}
