package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Repositories.GioHangRepository;
import Website.LaptopShop.Services.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository Rep;

    @Override
    public GioHang getGioHangByNguoiDung(NguoiDung n) {return Rep.findByNguoiDung(n);}

    @Override
    public GioHang save(GioHang g) {return Rep.save(g);}
}
