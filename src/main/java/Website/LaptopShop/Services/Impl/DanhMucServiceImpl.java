package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.DanhMuc;
import Website.LaptopShop.Repositories.DanhMucRepository;
import Website.LaptopShop.Services.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepository Rep;

    @Override
    public DanhMuc save(DanhMuc d) {return Rep.save(d);}

    @Override
    public void deleteById(long id) {Rep.deleteById(id);}

    @Override
    public Page<DanhMuc> getAllDanhMucForPageable(int page, int size) {
        return Rep.findAll(PageRequest.of(page, size));
    }

    @Override
    public DanhMuc getDanhMucById(long id) {return Rep.findById(id).get();}

    @Override
    public List<DanhMuc> getAllDanhMuc() {return Rep.findAll();}
}
