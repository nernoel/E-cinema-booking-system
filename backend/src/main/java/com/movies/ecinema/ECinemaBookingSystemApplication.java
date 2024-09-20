package com.movies.ecinema;


// Importing spring framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Importing dotenv variable loader
import io.github.cdimascio.dotenv.Dotenv;

// Importing model mapper
import org.modelmapper.ModelMapper;

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

		// Defining database environment variables
		String dbUser = dotenv.get("db_user");
		String dbName = dotenv.get("db_name");
		String dbPass = dotenv.get("db_pass");

		// Running the spring application
		SpringApplication.run(ECinemaBookingSystemApplication.class, args);
	}
}
