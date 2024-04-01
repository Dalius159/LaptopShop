package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.CartPointer;
import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartPointerRepository extends JpaRepository<CartPointer, Long> {
    CartPointer findByProductAndCart(Product product, Cart cart);
    List<CartPointer> findByCart(Cart cart);
}
