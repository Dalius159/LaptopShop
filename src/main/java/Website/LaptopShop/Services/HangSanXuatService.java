package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.HangSanXuat;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HangSanXuatService {

    Page<HangSanXuat> getALlHangSX(int page, int size);

    HangSanXuat getHSXById(long id);

    HangSanXuat save(HangSanXuat h);

    void deleteById(long id);
}
