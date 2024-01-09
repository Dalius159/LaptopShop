package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.ChiMucGioHang;
import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.SanPham;
import Website.LaptopShop.Repositories.ChiMucGioHangRepository;
import Website.LaptopShop.Services.ChiMucGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiMucGioHangServiceImpl implements ChiMucGioHangService {
    @Autowired
    private ChiMucGioHangRepository Rep;
    @Override
    public ChiMucGioHang getChiMucGioHangBySanPhamAndGioHang(SanPham sp, GioHang g) {
        return Rep.findBySanPhamAndGioHang(sp,g);
    }
    @Override
    public ChiMucGioHang saveChiMucGiohang(ChiMucGioHang c) {return Rep.save(c);}

    @Override
    public void deleteChiMucGiohang(ChiMucGioHang c) {Rep.delete(c);}

    @Override
    public List<ChiMucGioHang> getChiMucGioHangByGioHang(GioHang g) {return Rep.findByGioHang(g);}

    @Override
    public void deleteAllChiMucGiohang(List<ChiMucGioHang> c) {Rep.deleteAll(c);}
}
