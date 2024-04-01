package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(Users u);
}
