package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    List<Product> findFirst12ByCategoryCategoryNameContainingIgnoreCaseOrderByIdDesc(String category);
    List<Product> findByIdIn(Set<Long> idList);
}
