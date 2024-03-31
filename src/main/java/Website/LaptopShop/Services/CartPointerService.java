package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.CartPointer;
import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Product;

import java.util.List;

public interface CartPointerService {
    List<CartPointer> getCartPointerByCart(Cart cart);

    CartPointer getCartPointerByProductAndCart(Product product, Cart cart);

    CartPointer saveCartPointer(CartPointer cart);

    void deleteCartPointer(CartPointer cart);

    void deleteAllCartPointer(List<CartPointer> cartPointer);
}
