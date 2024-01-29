package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.SearchDonHangObject;
import Website.LaptopShop.Entities.DonHang;
import Website.LaptopShop.Entities.NguoiDung;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface DonHangService {
    Page<DonHang> getAllDonHangByFilter(SearchDonHangObject object, int page) throws ParseException;

    DonHang findById(long id);

    DonHang save(DonHang dh);

    List<Object> layDonHangTheoThangVaNam();

    List<DonHang> getDonHangByNguoiDung(NguoiDung currentUser);

    DonHang findLatestDonHangByMaNguoiDat(Long maNguoiDat);
}
