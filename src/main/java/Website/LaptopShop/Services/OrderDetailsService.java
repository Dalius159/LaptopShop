package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetails> save(List<OrderDetails> list);
}
