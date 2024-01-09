package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;
@Entity
@Data
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String diaChi;
    private String email;
    private String hoTen;

    @JsonIgnore
    private String password;

    private String soDienThoai;

    @ManyToMany
    @JoinTable(name="nguoidung_vaitro",
            joinColumns=@JoinColumn(name="ma_nguoi_dung"),
            inverseJoinColumns=@JoinColumn(name="ma_vai_tro"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VaiTro> vaiTro;

    @Transient
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<DonHang> listDonHang;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    public NguoiDung() {
        // TODO Auto-generated constructor stub
    }
}
