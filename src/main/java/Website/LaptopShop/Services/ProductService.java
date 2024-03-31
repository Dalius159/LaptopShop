package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.ProductDTO;
import Website.LaptopShop.DTO.SearchProductObject;
import Website.LaptopShop.Entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product save(ProductDTO sp);

    void deleteById(long id);

    Page<Product> getAllProductByFilter(SearchProductObject object, int page, int limit);

    Product getProductById(long id);

    List<Product> getLatestProduct();

    Iterable<Product> getProductByProductNameWithoutPaginate(SearchProductObject object);

    Page<Product> getProductByProductName(SearchProductObject object, int page, int resultPerPage);

    List<Product> getAllProductByList(Set<Long> idList);

    Iterable<Product> getProductByCategoryName(String category);

    public Page<Product> getProductByCategory(SearchProductObject object, int page, int resultPerPage);
}
