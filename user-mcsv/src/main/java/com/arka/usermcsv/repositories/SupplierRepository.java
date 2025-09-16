package com.arka.usermcsv.repositories;

import com.arka.usermcsv.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
