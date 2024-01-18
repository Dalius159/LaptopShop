package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Entity()
@Data
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String diaChiNhan;
    private String ghiChu;
    private String hoTenNguoiNhan;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date ngayDatHang;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date ngayGiaoHang;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date ngayNhanHang;

    private String sdtNhanHang;
    private String trangThaiDonHang;
    private long tongGiaTri;

    @ManyToOne()
    @JoinColumn(name = "ma_nguoi_dat")
    private NguoiDung nguoiDat;

    @ManyToOne()
    @JoinColumn(name = "ma_shipper")
    private NguoiDung shipper;

    @OneToMany(mappedBy = "donHang")
    private List<ChiTietDonHang> danhSachChiTiet;

    public DonHang() {
        // TODO Auto-generated constructor stub
    }
}
