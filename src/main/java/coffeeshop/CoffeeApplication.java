package coffeeshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner printHashedPassword(BCryptPasswordEncoder encoder) {
		return args -> {
			String plainPassword = "iloveyou";
			String hashedPassword = encoder.encode(plainPassword);
			System.out.println("Sample hashed password: " + hashedPassword);
		};
	}
}
