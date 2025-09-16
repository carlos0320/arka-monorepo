package com.arka.usermcsv.repositories;

import com.arka.usermcsv.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
   Optional<Client> findByCustomerName(String customerName);
}
