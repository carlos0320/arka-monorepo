package com.arka.usermcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserMcsvApplication {

   public static void main(String[] args) {
      SpringApplication.run(UserMcsvApplication.class, args);
   }

}
