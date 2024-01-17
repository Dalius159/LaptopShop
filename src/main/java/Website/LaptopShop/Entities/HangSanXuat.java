package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity(name = "hang_san_xuat")
@Data
public class HangSanXuat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message="Producer brand cannot be empty")
    private String tenHangSanXuat;

    @JsonIgnore
    @OneToMany(mappedBy = "hangSanXuat")
    private List<SanPham> listSanPham;
}
