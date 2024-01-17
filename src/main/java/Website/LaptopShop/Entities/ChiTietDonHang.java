package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity(name = "chi_tiet_don_hang")
@Data
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long donGia;
    private int soLuongDat;

    @ManyToOne
    @JoinColumn(name = "ma_don_hang")
    @JsonIgnore
    private DonHang donHang;

    @OneToOne
    @JoinColumn(name="ma_san_pham")
    private SanPham sanPham;

    private int soLuongNhanHang;
}
