package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.DonHang;
import Website.LaptopShop.Entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Long>, QuerydslPredicateExecutor<DonHang> {
    public List<DonHang> findByTrangThaiDonHangAndShipper(String trangThai, NguoiDung shipper);
    @Query(value = "select DATE_FORMAT(dh.ngayNhanHang, '%m') as month, "
            + " DATE_FORMAT(dh.ngayNhanHang, '%Y') as year, sum(ct.soLuongNhanHang * ct.donGia) as total "
            + " from DonHang dh, ChiTietDonHang ct"
            + " where dh.id = ct.donHang.id and dh.trangThaiDonHang ='Hoàn thành'"
            + " group by DATE_FORMAT(dh.ngayNhanHang, '%Y%m')"
            + " order by DATE_FORMAT(dh.ngayNhanHang, '%Y') asc" )
    public List<Object> layDonHangTheoThangVaNam();
    public List<DonHang> findByNguoiDat(NguoiDung nguoiDat);
    public int countByTrangThaiDonHang(String trangThaiDonHang);
}
