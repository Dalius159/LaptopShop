package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.NguoiDung;

public interface GioHangService {
    GioHang getGioHangByNguoiDung(NguoiDung n);

    GioHang save(GioHang g);
}
