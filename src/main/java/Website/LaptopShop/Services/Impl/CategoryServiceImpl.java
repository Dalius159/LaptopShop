package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Category;
import Website.LaptopShop.Repositories.CategoryRepository;
import Website.LaptopShop.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository Rep;

    @Override
    public Category save(Category category) {return Rep.save(category);}

    @Override
    public void deleteById(long id) {Rep.deleteById(id);}

    @Override
    public Page<Category> getAllCategoryForPageable(int page, int size) {
        return Rep.findAll(PageRequest.of(page, size));
    }

    @Override
    public Category getCategoryById(long id) {return Rep.findById(id).get();}

    @Override
    public List<Category> getAllCategory() {return Rep.findAll();}
}
