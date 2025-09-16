package com.arka.cartmcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaAuditing
//@EnableJpaRepositories(basePackages="com.arka.cartmcsv.infrastructure.adapters.repositories")
//@EntityScan(basePackages = "com.arka.cleanarchitecture.infrastructure.adapters.entities")
public class CartMcsvApplication {

   public static void main(String[] args) {
      SpringApplication.run(CartMcsvApplication.class, args);
   }

}
