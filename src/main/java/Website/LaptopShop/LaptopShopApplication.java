package Website.LaptopShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//	TODO: thymeleaf - spring boot integration eliminate the need for model DTOs
//	TODO: refactor thymeleaf fragments, dynamic images link for all, replace jquery responsive_slide with vanilla js
//	TODO: 'quantity' typo
//	TODO: change codebase to english
//	TODO: field injection to constructor injection

@SpringBootApplication
@EnableJpaRepositories("Website.LaptopShop.Repositories")
public class LaptopShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopShopApplication.class, args);
	}
}
