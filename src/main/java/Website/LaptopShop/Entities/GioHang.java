package Website.LaptopShop.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity(name = "gio_hang")
@Data
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tongTien;

    @OneToOne
    @JoinColumn(name = "ma_nguoi_dung")
    private NguoiDung nguoiDung;
}
