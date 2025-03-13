// Define the package for the application
package com.example.coffee;


// Import necessary Spring Boot classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// Main class to run the Spring Boot application
@SpringBootApplication
public class CoffeeApplication {

	// The main method is the entry point for the application
	public static void main(String[] args) {
		// Run the Spring Boot application
		// SpringApplication.run() bootstraps the application by starting the embedded server
		SpringApplication.run(CoffeeApplication.class, args);
	}

}
