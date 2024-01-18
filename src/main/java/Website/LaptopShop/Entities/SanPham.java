package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
@Entity(name = "san_pham")
@Data
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cpu;
    private long donGia;
    private int donViBan;
    private int donViKho;
    private int dungLuongPin_mAh;
    private String heDieuHanh;
    private String manHinh;
    private String ram;
    private String tenSanPham;
    private String thietKe;
    private String thongTinBaoHanh;
    private String thongTinChung;

    @ManyToOne
    @JoinColumn(name = "ma_danh_muc")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "ma_hang_sx")
    private HangSanXuat hangSanXuat;

    @Transient
    @JsonIgnore
    private MultipartFile hinhAnh;
}
