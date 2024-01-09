package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.ChiMucGioHang;
import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiMucGioHangRepository extends JpaRepository<ChiMucGioHang, Long> {
    ChiMucGioHang findBySanPhamAndGioHang(SanPham sanPham, GioHang gioHang);
    List<ChiMucGioHang> findByGioHang(GioHang gioHang);
}
