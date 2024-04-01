package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.CartPointer;
import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Repositories.CartPointerRepository;
import Website.LaptopShop.Services.CartPointerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartPointerServiceImpl implements CartPointerService {
    @Autowired
    private CartPointerRepository Rep;
    @Override
    public CartPointer getCartPointerByProductAndCart(Product product, Cart cart) {
        return Rep.findByProductAndCart(product,cart);
    }
    @Override
    public CartPointer saveCartPointer(CartPointer c) {return Rep.save(c);}

    @Override
    public void deleteCartPointer(CartPointer c) {Rep.delete(c);}

    @Override
    public List<CartPointer> getCartPointerByCart(Cart g) {return Rep.findByCart(g);}

    @Override
    public void deleteAllCartPointer(List<CartPointer> c) {Rep.deleteAll(c);}
}
