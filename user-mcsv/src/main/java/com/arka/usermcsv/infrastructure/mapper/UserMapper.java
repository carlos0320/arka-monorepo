package com.arka.usermcsv.infrastructure.mapper;

import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.infrastructure.entity.ClientEntity;
import com.arka.usermcsv.infrastructure.entity.SupplierEntity;
import com.arka.usermcsv.infrastructure.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Component
public class UserMapper {
  private final RoleMapper roleMapper = new RoleMapper();
  private final ClientMapper clientMapper = new ClientMapper();
  private final SupplierMapper supplierMapper = new SupplierMapper();

  public UserEntity toEntity(User domain) {
    if (domain == null) return null;
    UserEntity entity = new UserEntity();
    if (domain.getUserId() != null) {
      entity.setUserId(domain.getUserId());
    }
    entity.setName(domain.getName());
    entity.setEmail(domain.getEmail());
    entity.setPassword(domain.getPassword());
    entity.setPhone(domain.getPhone());
    entity.setAddress(domain.getAddress());
    entity.setActive(domain.isActive());

    if (domain.getRoles() != null)
      entity.setRoles(domain.getRoles().stream()
              .map(roleMapper::toEntity)
              .collect(Collectors.toSet()));

    if (domain.getClient() != null){
      ClientEntity clientEntity = clientMapper.toEntity(domain.getClient());
      clientEntity.setUser(entity);
      entity.setClient(clientEntity);
    }

    if (domain.getSupplier() != null){
      SupplierEntity supplierEntity = supplierMapper.toEntity(domain.getSupplier());
      supplierEntity.setUser(entity);
      entity.setSupplier(supplierEntity);
    }
      entity.setSupplier(supplierMapper.toEntity(domain.getSupplier()));

    return entity;
  }

  public User toDomain(UserEntity entity) {
    if (entity == null) return null;
    User domain = new User();
    domain.setUserId(entity.getUserId());
    domain.setName(entity.getName());
    domain.setEmail(entity.getEmail());
    domain.setPassword(entity.getPassword());
    domain.setPhone(entity.getPhone());
    domain.setAddress(entity.getAddress());
    domain.setActive(entity.isActive());

    if (entity.getRoles() != null)
      domain.setRoles(entity.getRoles().stream()
              .map(roleMapper::toDomain)
              .collect(Collectors.toSet()));

    if (entity.getClient() != null)
      domain.setClient(clientMapper.toDomain(entity.getClient()));

    if (entity.getSupplier() != null)
      domain.setSupplier(supplierMapper.toDomain(entity.getSupplier()));

    return domain;
  }

}
