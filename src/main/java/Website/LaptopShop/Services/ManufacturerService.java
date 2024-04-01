package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Manufacturer;
import org.springframework.data.domain.Page;

public interface ManufacturerService {

    Page<Manufacturer> getALlManufacturer(int page, int size);

    Manufacturer getManufacturerById(long id);

    Manufacturer save(Manufacturer manufacturer);

    void deleteById(long id);
}
