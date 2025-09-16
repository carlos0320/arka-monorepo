package com.arka.inventorymcsv.repository;

import com.arka.inventorymcsv.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
   Optional<Inventory> findByProductId(Long productId);
}
