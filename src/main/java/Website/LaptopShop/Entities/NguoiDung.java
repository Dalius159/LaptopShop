package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Set;
@Entity()
@Data
@Table(name = "nguoi_dung", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String diaChi;
    private String email;

    @NotEmpty
    private String hoTen;

    @JsonIgnore
    private String password;

    private String soDienThoai;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="nguoidung_vaitro",
            joinColumns=@JoinColumn(name="ma_nguoi_dung", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="ma_vai_tro", referencedColumnName = "id"))
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
