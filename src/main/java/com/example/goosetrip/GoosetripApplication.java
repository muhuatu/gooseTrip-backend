package com.example.goosetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GoosetripApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoosetripApplication.class, args);
	}

}
