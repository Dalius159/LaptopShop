package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.DTO.SearchLienHeObject;
import Website.LaptopShop.Entities.LienHe;
import Website.LaptopShop.Entities.QLienHe;
import Website.LaptopShop.Repositories.LienHeRepository;
import Website.LaptopShop.Services.LienHeService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class LienHeServiceImpl implements LienHeService {
    @Autowired
    private LienHeRepository Rep;

    @Override
    public Page<LienHe> getLienHeByFilter(SearchLienHeObject object, int page) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        String trangThai = object.getTrangThaiLienHe();
        String tuNgay = object.getTuNgay();
        String denNgay = object.getDenNgay();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        if (!trangThai.equals("")) {
            builder.and(QLienHe.lienHe.trangThai.eq(trangThai));
        }

        if (!tuNgay.equals("") && tuNgay != null) {
            builder.and(QLienHe.lienHe.ngayLienHe.goe(formatDate.parse(tuNgay)));
        }

        if (!denNgay.equals("") && denNgay != null) {
            builder.and(QLienHe.lienHe.ngayLienHe.loe(formatDate.parse(denNgay)));
        }

        return Rep.findAll(builder, PageRequest.of(page - 1, 2));
    }

    @Override
    public LienHe findById(long id) {
        return Rep.findById(id).get();
    }

    @Override
    public LienHe save(LienHe lh) {
        return Rep.save(lh);
    }

    @Override
    public int countByTrangThai(String trangThai) {
        return Rep.countByTrangThai(trangThai);
    }
}
