package com.movies.ecinema;

// Importing spring framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


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


		// Running the spring application
		SpringApplication.run(ECinemaBookingSystemApplication.class, args);
	}
}
