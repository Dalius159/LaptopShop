package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class HangSanXuat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message="Tên hãng sản xuất không được trống")
    private String tenHangSanXuat;

    @JsonIgnore
    @OneToMany(mappedBy = "hangSanXuat")
    private List<SanPham> listSanPham;
}
