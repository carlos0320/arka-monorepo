package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.exception.EmailAlreadyExistsException;
import com.arka.usermcsv.domain.exception.RoleNotFoundException;
import com.arka.usermcsv.domain.model.Client;
import com.arka.usermcsv.domain.model.Role;
import com.arka.usermcsv.domain.model.RoleTypes;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.domain.model.gateway.RoleGateway;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

  @Mock
  private UserGateway userGateway;

  @Mock
  private RoleGateway roleGateway;

  private CreateUserUseCase useCase;

  @BeforeEach
  public void setUp() {
    useCase = new CreateUserUseCase(userGateway, roleGateway);
  }

  @Test
  void shouldReturnAnUser_whenRegisterIsValid() {
    User inputUser = new User();
    inputUser.setEmail("test@test.com");
    inputUser.setPassword("pass");

    Set<RoleTypes> roleTypes = Set.of(RoleTypes.ADMIN, RoleTypes.CLIENT);

    when(userGateway.existsByEmail("test@test.com")).thenReturn(false);

    User savedUser = new User();
    savedUser.setUserId(1L);
    savedUser.setEmail("test@test.com");

    when(userGateway.createUser(any(User.class))).thenReturn(savedUser);

    Role roleAdmin = new Role(1L, RoleTypes.ADMIN);
    Role roleClient = new Role(2L, RoleTypes.CLIENT);

    when(roleGateway.findByRoleType("admin")).thenReturn(Optional.of(roleAdmin));
    when(roleGateway.findByRoleType("client")).thenReturn(Optional.of(roleClient));

    User updatedUser = new User();
    updatedUser.setUserId(1L);
    updatedUser.setRoles(Set.of(roleAdmin, roleClient));

    when(userGateway.updateUser(any(User.class))).thenReturn(updatedUser);

    User result = useCase.execute(inputUser, roleTypes, null, null);

    assertThat(result.getRoles()).containsExactlyInAnyOrder(roleAdmin, roleClient);
  }

  @Test
  void shouldThrowException_whenEmailAlreadyExists() {
    User inputUser = new User();
    inputUser.setEmail("emailtest");

    when(userGateway.existsByEmail("emailtest")).thenReturn(true);

    assertThatThrownBy(() -> useCase.execute(inputUser, Set.of(RoleTypes.ADMIN), null, null))
            .isInstanceOf(EmailAlreadyExistsException.class);

    verify(userGateway).existsByEmail("emailtest");
  }

  @Test
  void shouldThrowException_whenRoleDoesNotExist() {
    User inputUser = new User();
    inputUser.setEmail("test@test.com");

    when(userGateway.existsByEmail("test@test.com")).thenReturn(false);

    when(roleGateway.findByRoleType("admin")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> useCase.execute(inputUser, Set.of(RoleTypes.ADMIN), null, null))
            .isInstanceOf(RoleNotFoundException.class);
  }
}
