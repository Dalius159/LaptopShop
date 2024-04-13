package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Manufacturer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getALlManufacturer();

    Page<Manufacturer> getALlManufacturer(int page, int size);

    Manufacturer getManufacturerById(long id);

    Manufacturer save(Manufacturer manufacturer);

    void deleteById(long id);
}
