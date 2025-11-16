package com.arka.usermcsv.infrastructure.repository;

import com.arka.usermcsv.domain.model.Client;
import com.arka.usermcsv.infrastructure.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
  Optional<ClientEntity> findByCustomerNameIgnoreCase(String customerName);
}
