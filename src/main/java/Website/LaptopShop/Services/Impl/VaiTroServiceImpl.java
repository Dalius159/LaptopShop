package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.VaiTro;
import Website.LaptopShop.Repositories.VaiTroRepository;
import Website.LaptopShop.Services.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaiTroServiceImpl implements VaiTroService {
    @Autowired
    private VaiTroRepository Rep;

    @Override
    public VaiTro findByTenVaiTro(String tenVaiTro) {
        return Rep.findByTenVaiTro(tenVaiTro);
    }

    @Override
    public List<VaiTro> findAllVaiTro() {
        return Rep.findAll();
    }
}
