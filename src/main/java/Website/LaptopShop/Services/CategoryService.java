package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<Category> getAllCategoryForPageable(int page, int size);

    List<Category> getAllCategory();

    Category getCategoryById(long id);

    Category save(Category category);

    void deleteById(long id);
}
