package com.arka.usermcsv.repositories;

import com.arka.usermcsv.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
