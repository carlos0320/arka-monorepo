package com.arka.productmicroservice.repositories;

import com.arka.productmicroservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
   Optional<Brand> findByName(String name);
}
