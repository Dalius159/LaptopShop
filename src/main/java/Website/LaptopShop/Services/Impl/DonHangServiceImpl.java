package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.DTO.SearchDonHangObject;
import Website.LaptopShop.Entities.DonHang;
import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Entities.QDonHang;
import Website.LaptopShop.Repositories.DonHangRepository;
import Website.LaptopShop.Services.DonHangService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository Rep;

    @Override
    public Page<DonHang> getAllDonHangByFilter(SearchDonHangObject object, int page) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        String trangThaiDon = object.getTrangThaiDon();
        String tuNgay = object.getTuNgay();
        String denNgay = object.getDenNgay();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        if (!trangThaiDon.equals("")) {
            builder.and(QDonHang.donHang.trangThaiDonHang.eq(trangThaiDon));
        }

        if (!tuNgay.equals("") && tuNgay != null) {
            if (trangThaiDon.equals("") || trangThaiDon.equals("Đang chờ giao") || trangThaiDon.equals("Đã hủy")) {
                builder.and(QDonHang.donHang.ngayDatHang.goe(formatDate.parse(tuNgay)));
            } else if (trangThaiDon.equals("Đang giao")) {
                builder.and(QDonHang.donHang.ngayGiaoHang.goe(formatDate.parse(tuNgay)));
            } else { // hoàn thành
                builder.and(QDonHang.donHang.ngayNhanHang.goe(formatDate.parse(tuNgay)));
            }
        }

        if (!denNgay.equals("") && denNgay != null) {
            if (trangThaiDon.equals("") || trangThaiDon.equals("Đang chờ giao") || trangThaiDon.equals("Đã hủy")) {
                builder.and(QDonHang.donHang.ngayDatHang.loe(formatDate.parse(denNgay)));
            } else if (trangThaiDon.equals("Đang giao")) {
                builder.and(QDonHang.donHang.ngayGiaoHang.loe(formatDate.parse(denNgay)));
            } else { // hoàn thành
                builder.and(QDonHang.donHang.ngayNhanHang.loe(formatDate.parse(denNgay)));
            }
        }

        return Rep.findAll(builder, PageRequest.of(page - 1, 6));
    }

    @Override
    public DonHang findById(long id) {return Rep.findById(id).get();}

    @Override
    public DonHang save(DonHang dh) {
        return Rep.save(dh);
    }

    @Override
    public List<Object> layDonHangTheoThangVaNam() {return Rep.layDonHangTheoThangVaNam();}

    @Override
    public List<DonHang> getDonHangByNguoiDung(NguoiDung ng) {return Rep.findByNguoiDat(ng);}

    @Override
    public int countByTrangThaiDonHang(String trangThaiDonHang) {
        return Rep.countByTrangThaiDonHang(trangThaiDonHang);
    }
}
