package Website.LaptopShop.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity()
@Data
public class ChiMucGioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int soLuong;

    @ManyToOne
    @JoinColumn(name = "ma_gio_hang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "ma_san_pham")
    private SanPham sanPham;
}
