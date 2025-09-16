package com.arka.usermcsv.repositories;

import com.arka.usermcsv.entities.Role;
import com.arka.usermcsv.entities.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByRoleType(RoleTypes roleType);
}
