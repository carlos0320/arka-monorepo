package com.arka.usermcsv.infrastructure.mapper;

import com.arka.usermcsv.domain.model.Role;
import com.arka.usermcsv.domain.model.RoleTypes;
import com.arka.usermcsv.infrastructure.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class RoleMapper {
  public RoleEntity toEntity(Role role) {
    if (role == null) return null;
    RoleEntity entity = new RoleEntity();
    entity.setRoleId(role.getRoleId());
    entity.setRoleType(role.getRoleType().getValue());
    return entity;
  }

  public Role toDomain(RoleEntity entity) {
    if (entity == null) return null;
    Role role = new Role();
    role.setRoleId(entity.getRoleId());
    role.setRoleType(RoleTypes.valueOf(entity.getRoleType().toUpperCase()));
    return role;
  }
}
