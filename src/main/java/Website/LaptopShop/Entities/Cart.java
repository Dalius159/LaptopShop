package Website.LaptopShop.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity()
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String totalCost;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
