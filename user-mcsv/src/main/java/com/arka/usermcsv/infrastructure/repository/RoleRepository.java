package com.arka.usermcsv.infrastructure.repository;

import com.arka.usermcsv.domain.model.RoleTypes;
import com.arka.usermcsv.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByRoleType(String type);
}
