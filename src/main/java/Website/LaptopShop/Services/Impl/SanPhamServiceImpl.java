package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.DTO.SanPhamDTO;
import Website.LaptopShop.DTO.SearchSanPhamObject;
import Website.LaptopShop.Entities.QSanPham;
import Website.LaptopShop.Entities.SanPham;
import Website.LaptopShop.Repositories.DanhMucRepository;
import Website.LaptopShop.Repositories.HangSanXuatRepository;
import Website.LaptopShop.Repositories.SanPhamRepository;
import Website.LaptopShop.Services.SanPhamService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepo;

    @Autowired
    private DanhMucRepository danhMucRepo;

    @Autowired
    private HangSanXuatRepository hangSanXuatRepo;

    // đổi từ SanPhamDto sang đối tượng SanPham để add vào db
    public SanPham convertFromSanPhamDto(SanPhamDTO dto) {
        SanPham sanPham = new SanPham();
        if (!dto.getId().equals("")) {
            sanPham.setId(Long.parseLong(dto.getId()));
        }
        sanPham.setTenSanPham(dto.getTenSanPham());
        sanPham.setCpu(dto.getCpu());
        sanPham.setDanhMuc(danhMucRepo.findById(dto.getDanhMucId()).get());
        sanPham.setHangSanXuat(hangSanXuatRepo.findById(dto.getNhaSXId()).get());
        sanPham.setDonGia(Long.parseLong(dto.getDonGia()));
        sanPham.setThietKe(dto.getThietKe());
        sanPham.setThongTinBaoHanh(dto.getThongTinBaoHanh());
        sanPham.setThongTinChung(dto.getThongTinChung());
        sanPham.setManHinh(dto.getManHinh());
        sanPham.setRam(dto.getRam());
        sanPham.setDungLuongPin_mAh(dto.getDungLuongPin());
        sanPham.setDonViKho(Integer.parseInt(dto.getDonViKho()));
        sanPham.setHeDieuHanh(dto.getHeDieuHanh());

        return sanPham;
    }

    @Override
    public SanPham save(SanPhamDTO dto) {return sanPhamRepo.save(convertFromSanPhamDto(dto));}

    @Override
    public void deleteById(long id) {
        sanPhamRepo.deleteById(id);

    }

    public void FilterByPrice(String price, BooleanBuilder builder){
        // tim theo don gia
        switch (price) {
            case "Under 400":
                builder.and(QSanPham.sanPham.donGia.lt(400));
                break;

            case "400 - 800":
                builder.and(QSanPham.sanPham.donGia.between(400, 800));
                break;

            case "800 - 1200":
                builder.and(QSanPham.sanPham.donGia.between(800, 1200));
                break;

            case "1200 - 1600":
                builder.and(QSanPham.sanPham.donGia.between(1200, 1600));
                break;

            case "Above 1000":
                builder.and(QSanPham.sanPham.donGia.gt(1600));
                break;
            default:
                break;
        }
    }

    @Override
    public Page<SanPham> getAllSanPhamByFilter(SearchSanPhamObject object, int page, int limit) {
        BooleanBuilder builder = new BooleanBuilder();
        String price = object.getDonGia();

        // sắp xếp theo giá
        Sort sort = Sort.by(Sort.Direction.ASC, "donGia"); // mặc định tăng dần
        if (object.getSapXepTheoGia().equals("desc")) { // giảm dần
            sort = Sort.by(Sort.Direction.DESC, "donGia");
        }

        if (!object.getDanhMucId().equals("") && object.getDanhMucId() != null) {
            builder.and(QSanPham.sanPham.danhMuc.eq(danhMucRepo.findById(Long.parseLong(object.getDanhMucId())).get()));
        }

        if (!object.getHangSXId().equals("") && object.getHangSXId() != null) {
            builder.and(QSanPham.sanPham.hangSanXuat
                    .eq(hangSanXuatRepo.findById(Long.parseLong(object.getHangSXId())).get()));
        }

        // tim theo don gia
        FilterByPrice(price, builder);
        return sanPhamRepo.findAll(builder, PageRequest.of(page, limit, sort));
    }

    @Override
    public List<SanPham> getLatestSanPham() {
        return sanPhamRepo.findFirst12ByDanhMucTenDanhMucContainingIgnoreCaseOrderByIdDesc("Laptop");
    }

    public Iterable<SanPham> getSanPhamByTenSanPhamWithoutPaginate(SearchSanPhamObject object) {
        BooleanBuilder builder = new BooleanBuilder();
        String[] keywords = object.getKeyword();
        String price = object.getDonGia();
        // Keyword
        builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[0] + "%"));
        if (keywords.length > 1) {
            for (int i = 1; i < keywords.length; i++) {
                builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[i] + "%"));
            }
        }
        FilterByPrice(price, builder);
        return sanPhamRepo.findAll(builder);
    }

    @Override
    public SanPham getSanPhamById(long id) {return sanPhamRepo.findById(id).get();}

    // Tim kiem san pham theo keyword, sap xep, phan trang, loc theo muc gia, lay 12
    // san pham moi trang
    @Override
    public Page<SanPham> getSanPhamByTenSanPham(SearchSanPhamObject object, int page, int resultPerPage) {
        BooleanBuilder builder = new BooleanBuilder();
        String[] keywords = object.getKeyword();
        String sort = object.getSort();
        String price = object.getDonGia();
        String brand = object.getBrand();
        String manufactor = object.getManufactor();
        // Keyword
        builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[0] + "%"));
        if (keywords.length > 1) {
            for (int i = 1; i < keywords.length; i++) {
                builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[i] + "%"));
            }
        }
        // Muc gia
        FilterByPrice(price, builder);

        // Danh muc va hang san xuat
        if (brand.length()>1) {
            builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
        }
        if (manufactor.length()>1) {
            builder.and(QSanPham.sanPham.hangSanXuat.tenHangSanXuat.eq(manufactor));
        }

        // Sap xep
        if (sort.equals("newest")) {
            return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "id"));
        } else if (sort.equals("priceAsc")) {
            return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.ASC, "donGia"));
        } else if (sort.equals("priceDes")) {
            return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "donGia"));
        }
        return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage));
    }

    public List<SanPham> getAllSanPhamByList(Set<Long> idList) {
        return sanPhamRepo.findByIdIn(idList);
    }

    @Override
    public Iterable<SanPham> getSanPhamByTenDanhMuc(String brand) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
        return sanPhamRepo.findAll(builder);
    }

    @Override
    public Page<SanPham> getSanPhamByBrand(SearchSanPhamObject object, int page, int resultPerPage) {
        BooleanBuilder builder = new BooleanBuilder();
        String price = object.getDonGia();
        String brand = object.getBrand();
        String manufactor = object.getManufactor();
        String os = object.getOs();
        String ram = object.getRam();
        int pin = object.getPin();
        // Muc gia
        FilterByPrice(price, builder);

        // Danh muc va hang san xuat
        if (brand.length()>1) {
            builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
        }
        if (manufactor.length()>1) {
            builder.and(QSanPham.sanPham.hangSanXuat.tenHangSanXuat.eq(manufactor));
        }
        if (os.length()>1) {
            builder.and(QSanPham.sanPham.heDieuHanh.like("%"+os+"%"));
        }
        if (ram.length()>1) {
            builder.and(QSanPham.sanPham.ram.like("%"+ram+"%"));
        }
        if (pin>1000) {
            builder.and(QSanPham.sanPham.dungLuongPin_mAh.eq(pin));
        }

        return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage));
    }
}
