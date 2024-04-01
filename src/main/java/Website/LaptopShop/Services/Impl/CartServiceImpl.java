package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Repositories.CartRepository;
import Website.LaptopShop.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository Rep;

    @Override
    public Cart getCartByUser(Users user) {return Rep.findByUser(user);}

    @Override
    public Cart save(Cart c) {return Rep.save(c);}
}
