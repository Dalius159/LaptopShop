package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity()
@Data
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message="Manufacturer name can't be empty")
    @Column(unique = true)
    private String manufacturerName;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> listProduct;
}
