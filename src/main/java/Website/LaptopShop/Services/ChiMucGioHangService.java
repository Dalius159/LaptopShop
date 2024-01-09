package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.ChiMucGioHang;
import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.SanPham;

import java.util.List;

public interface ChiMucGioHangService {
    List<ChiMucGioHang> getChiMucGioHangByGioHang(GioHang g);

    ChiMucGioHang getChiMucGioHangBySanPhamAndGioHang(SanPham sp, GioHang g);

    ChiMucGioHang saveChiMucGiohang(ChiMucGioHang c);

    void deleteChiMucGiohang(ChiMucGioHang c);

    void deleteAllChiMucGiohang(List<ChiMucGioHang> c);
}
