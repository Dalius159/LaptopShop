package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
@Entity()
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cpu;
    private long price;
    private int salesUnit;
    private int warehouseUnit;
    private int batteryCapacity_mAh;
    private String operatingSystem;
    private String screen;
    private String ram;
    private String productName;
    private String design;
    private String warrantyInfor;
    private String generalInfor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Transient
    @JsonIgnore
    private MultipartFile image;
}
