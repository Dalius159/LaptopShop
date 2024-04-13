package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Manufacturer;
import Website.LaptopShop.Repositories.ManufacturerRepository;
import Website.LaptopShop.Services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    private ManufacturerRepository Rep;

    @Override
    public Manufacturer getManufacturerById(long id) {return Rep.findById(id).get();}

    @Override
    public Manufacturer save(Manufacturer manufacturer) {return Rep.save(manufacturer);}

    @Override
    public void deleteById(long id) {Rep.deleteById(id);}

    @Override
    public Page<Manufacturer> getALlManufacturer(int page, int size) {
        return Rep.findAll(PageRequest.of(page, size));}

    @Override
    public List<Manufacturer> getALlManufacturer() {
        return Rep.findAll();
    }
}
