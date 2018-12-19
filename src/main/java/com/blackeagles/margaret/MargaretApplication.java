package com.blackeagles.margaret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MargaretApplication {

  public static void main(String[] args) {
    SpringApplication.run(MargaretApplication.class, args);
  }

}
