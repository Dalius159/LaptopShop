package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.SanPhamDTO;
import Website.LaptopShop.DTO.SearchSanPhamObject;
import Website.LaptopShop.Entities.SanPham;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface SanPhamService {
    SanPham save(SanPhamDTO sp);

    void deleteById(long id);

    Page<SanPham> getAllSanPhamByFilter(SearchSanPhamObject object, int page, int limit);

    SanPham getSanPhamById(long id);

    List<SanPham> getLatestSanPham();

    Iterable<SanPham> getSanPhamByTenSanPhamWithoutPaginate(SearchSanPhamObject object);

    Page<SanPham> getSanPhamByTenSanPham(SearchSanPhamObject object,int page, int resultPerPage);

    List<SanPham> getAllSanPhamByList(Set<Long> idList);

    Iterable<SanPham> getSanPhamByTenDanhMuc(String brand);

    public Page<SanPham> getSanPhamByBrand(SearchSanPhamObject object, int page, int resultPerPage);
}
