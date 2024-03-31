package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
