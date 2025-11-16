package com.arka.usermcsv.domain.model.gateway;

import com.arka.usermcsv.domain.model.Role;
import com.arka.usermcsv.domain.model.RoleTypes;

import java.util.Optional;

public interface RoleGateway {
  Optional<Role> findByRoleType(String type);
  Role save(Role role);
}
