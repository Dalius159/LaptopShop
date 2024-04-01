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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message="Manufacturer can't be empty")
    private String manufacturerName;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> listProduct;
}
