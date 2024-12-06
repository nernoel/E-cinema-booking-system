package ecinema.booking.system;

// Importing spring framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

// Importing model mapper
import org.modelmapper.ModelMapper;

// Importing environment variable loader
import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
//@EnableJpaRepositories(basePackages = "ecinema.booking.system.repository")
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

		System.setProperty("SMTP_HOST", dotenv.get("SMTP_HOST"));
		System.setProperty("SMTP_PASS", dotenv.get("SMTP_PASS"));
		System.setProperty("SMTP_USER", dotenv.get("SMTP_USER"));

		//System.setProperty("SENDER_ADDRESS", dotenv.get("SENDER_ADDRESS"));
		

		// Running the spring application
		SpringApplication.run(ECinemaBookingSystemApplication.class, args);
		}

		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
	}
}