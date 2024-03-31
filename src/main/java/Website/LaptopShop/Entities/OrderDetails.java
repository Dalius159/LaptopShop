package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity()
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long cost;
    private int orderQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders order;

    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int receivedQuantity;
}
