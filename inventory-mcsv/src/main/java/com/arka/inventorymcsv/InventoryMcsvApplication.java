package com.arka.inventorymcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryMcsvApplication {

   public static void main(String[] args) {
      SpringApplication.run(InventoryMcsvApplication.class, args);
   }

}
