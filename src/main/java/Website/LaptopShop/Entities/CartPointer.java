package Website.LaptopShop.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity()
@Data
public class CartPointer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
