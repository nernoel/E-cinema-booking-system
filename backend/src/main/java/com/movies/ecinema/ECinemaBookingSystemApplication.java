package com.movies.ecinema;

// Importing spring framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Importing dotenv variable loader
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ECinemaBookingSystemApplication {

	// Main function to run the application
	public static void main(String[] args) {
		// Loading environment variables
		Dotenv dotenv = Dotenv.load();
		String dbUser = dotenv.get("db_user");
		String dbName = dotenv.get("db_name");
		String dbPass = dotenv.get("db_pass");

		// Running the spring application
		SpringApplication.run(ECinemaBookingSystemApplication.class, args);
	}
}
