package com.arka.cartmcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CartMcsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMcsvApplication.class, args);
	}

}
