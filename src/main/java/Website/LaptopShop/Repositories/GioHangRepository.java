package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    GioHang findByNguoiDung(NguoiDung nguoiDung);
}
