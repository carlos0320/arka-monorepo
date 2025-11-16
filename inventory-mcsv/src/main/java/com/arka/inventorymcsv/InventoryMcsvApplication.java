package com.arka.inventorymcsv;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class InventoryMcsvApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryMcsvApplication.class, args);
  }

}
