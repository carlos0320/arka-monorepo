package com.arka.usermcsv.domain.usecase;

import com.arka.usermcsv.domain.model.*;
import com.arka.usermcsv.domain.model.gateway.ClientGateway;
import com.arka.usermcsv.domain.model.gateway.RoleGateway;
import com.arka.usermcsv.domain.model.gateway.SupplierGateway;
import com.arka.usermcsv.domain.model.gateway.UserGateway;

import java.util.Set;
import java.util.stream.Collectors;

public class CreateUserUseCase {
  private final UserGateway userGateway;
  private final RoleGateway roleGateway;
  private final ClientGateway clientGateway;
  private final SupplierGateway supplierGateway;


  public CreateUserUseCase(UserGateway userGateway, RoleGateway roleGateway, ClientGateway clientGateway, SupplierGateway supplierGateway) {
    this.userGateway = userGateway;
    this.roleGateway = roleGateway;
    this.clientGateway = clientGateway;
    this.supplierGateway = supplierGateway;
  }

  public User execute(User user, Set<RoleTypes> roleTypes, Client client, Supplier supplier) {
    if (userGateway.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email already registered");
    }

    // Save user first
    User savedUser = userGateway.createUser(user);

    // Save optional Client
    if (client != null) {
      client.setUserId(savedUser.getUserId());
      savedUser.setClient(client);
    }

    // Save optional Supplier
    if (supplier != null) {
      supplier.setUserId(savedUser.getUserId());
      savedUser.setSupplier(supplier);
    }

    Set<Role> roles = roleTypes.stream()
            .map(type -> roleGateway.findByRoleType(type.getValue())
                    .orElseThrow(() -> new IllegalArgumentException("Role type not found")))
            .collect(Collectors.toSet());

    savedUser.setRoles(roles);

    savedUser = userGateway.updateUser(savedUser);
    return savedUser;
  }
}
