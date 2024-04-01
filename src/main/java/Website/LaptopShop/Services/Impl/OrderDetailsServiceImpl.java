package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.OrderDetails;
import Website.LaptopShop.Repositories.OrderDetailsRepository;
import Website.LaptopShop.Services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository Rep;

    @Override
    public List<OrderDetails> save(List<OrderDetails> list) {return Rep.saveAll(list);}
}
