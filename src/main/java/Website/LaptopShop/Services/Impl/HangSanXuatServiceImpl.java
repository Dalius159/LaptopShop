package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.HangSanXuat;
import Website.LaptopShop.Repositories.HangSanXuatRepository;
import Website.LaptopShop.Services.HangSanXuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangSanXuatServiceImpl implements HangSanXuatService {
    @Autowired
    private HangSanXuatRepository Rep;

    @Override
    public List<HangSanXuat> getALlHangSX() {return Rep.findAll();}

    @Override
    public HangSanXuat getHSXById(long id) {return Rep.findById(id).get();}

    @Override
    public HangSanXuat save(HangSanXuat h) {return Rep.save(h);}

    @Override
    public void deleteById(long id) {Rep.deleteById(id);}

    @Override
    public Page<HangSanXuat> getALlHangSX(int page, int size) {return Rep.findAll(PageRequest.of(page, size));}
}
