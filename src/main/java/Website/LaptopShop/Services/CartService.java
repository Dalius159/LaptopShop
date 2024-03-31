package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Users;

public interface CartService {
    Cart getCartByUser(Users user);
    Cart save(Cart cart);
}
