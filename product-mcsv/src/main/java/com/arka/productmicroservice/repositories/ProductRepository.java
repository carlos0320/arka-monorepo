package com.arka.productmicroservice.repositories;

import com.arka.productmicroservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
   Optional<Product> findByName(String name);
   List<Product> findByCategoryName(String name);
}
