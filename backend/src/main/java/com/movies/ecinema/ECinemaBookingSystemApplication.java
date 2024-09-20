package com.movies.ecinema;

// Importing spring framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


// Importing model mapper
import org.modelmapper.ModelMapper;

// Importing environment variable loader
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ECinemaBookingSystemApplication {

	@Bean
	// Maps entity class to data access object
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}

	// Main function to run the application
	public static void main(String[] args) {
		// Loading environment variables
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Running the spring application
		SpringApplication.run(ECinemaBookingSystemApplication.class, args);
	}
}
