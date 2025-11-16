package com.arka.ordermcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderMcsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMcsvApplication.class, args);
	}

}
