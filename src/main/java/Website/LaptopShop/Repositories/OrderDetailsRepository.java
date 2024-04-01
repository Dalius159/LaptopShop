package Website.LaptopShop.Repositories;


import Website.LaptopShop.Entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {}
