package com.arka.productmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductMicroserviceApplication {

   public static void main(String[] args) {
      SpringApplication.run(ProductMicroserviceApplication.class, args);
   }

}
