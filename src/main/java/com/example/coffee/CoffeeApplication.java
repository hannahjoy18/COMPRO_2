package com.example.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

	private static void run() {
		String plainPassword = "mybff";
		String hash = new BCryptPasswordEncoder().encode(plainPassword);
		System.out.println(hash);
	}

}