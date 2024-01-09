package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaiTroRepository extends JpaRepository<VaiTro, Long> {
    VaiTro findByTenVaiTro(String tenVaiTro);
}
