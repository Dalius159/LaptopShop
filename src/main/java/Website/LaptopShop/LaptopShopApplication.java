package Website.LaptopShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//	TODO: thymeleaf - spring boot integration eliminate the need for model DTOs
//	TODO: field injection to constructor injection
//	TODO: fix store sort, fix search filter
//	TODO: add customer info edit
//	TODO: db scheme and MVC models need enum, error prone

@SpringBootApplication
@EnableJpaRepositories("Website.LaptopShop.Repositories")
public class LaptopShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopShopApplication.class, args);
	}
}
