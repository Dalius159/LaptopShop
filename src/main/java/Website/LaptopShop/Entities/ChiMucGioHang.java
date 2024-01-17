package Website.LaptopShop.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity(name = "chi_muc_gio_hang")
@Data
public class ChiMucGioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "soLuong")
    private int soLuong;

    @ManyToOne
    @JoinColumn(name = "ma_gio_hang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "ma_san_pham")
    private SanPham sanPham;
}
