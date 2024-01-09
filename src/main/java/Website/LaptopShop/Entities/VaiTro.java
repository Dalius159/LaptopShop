package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Data
public class VaiTro {
    @Id
    @GeneratedValue
    private long id;

    private String tenVaiTro;

    @JsonIgnore
    @ManyToMany(mappedBy = "vaiTro")
    private Set<NguoiDung> nguoiDung;

    public VaiTro() {
        // TODO Auto-generated constructor stub
    }
}
