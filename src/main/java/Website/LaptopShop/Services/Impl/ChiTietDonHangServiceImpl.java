package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.ChiTietDonHang;
import Website.LaptopShop.Repositories.ChiTietDonHangRepository;
import Website.LaptopShop.Services.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository Rep;

    @Override
    public List<ChiTietDonHang> save(List<ChiTietDonHang> list) {return Rep.saveAll(list);}
}
