package com.arka.usermcsv.infrastructure.adapter;

import com.arka.usermcsv.domain.model.Role;
import com.arka.usermcsv.domain.model.RoleTypes;
import com.arka.usermcsv.domain.model.gateway.RoleGateway;
import com.arka.usermcsv.infrastructure.entity.RoleEntity;
import com.arka.usermcsv.infrastructure.mapper.RoleMapper;
import com.arka.usermcsv.infrastructure.repository.RoleRepository;

import java.util.Optional;

public class RoleGatewayImpl implements RoleGateway {
  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleGatewayImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }


  @Override
  public Optional<Role> findByRoleType(String type) {
    return roleRepository.findByRoleType(type)
            .map(roleMapper::toDomain);
  }

  @Override
  public Role save(Role role) {
    RoleEntity entity = roleMapper.toEntity(role);
    return roleMapper.toDomain(roleRepository.save(entity));
  }
}
