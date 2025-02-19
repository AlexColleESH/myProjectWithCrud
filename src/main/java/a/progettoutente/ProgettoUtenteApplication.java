package a.progettoutente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProgettoUtenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoUtenteApplication.class, args);
	}

	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
