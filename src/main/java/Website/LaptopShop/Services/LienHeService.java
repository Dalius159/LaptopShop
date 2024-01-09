package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.SearchLienHeObject;
import Website.LaptopShop.Entities.LienHe;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface LienHeService {
    Page<LienHe> getLienHeByFilter(SearchLienHeObject object, int page) throws ParseException;

    LienHe findById(long id);

    LienHe save(LienHe lh);

    int countByTrangThai(String trangThai);
}
