package verificando.uy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"verificando.uy.controllers", "verificando.uy.services", "verificando.uy.repositories"})
public class VerificandoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerificandoApplication.class, args);
	}

}
