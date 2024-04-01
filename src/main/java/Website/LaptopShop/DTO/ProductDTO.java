package Website.LaptopShop.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ProductDTO {
    private String id;
    private String productName;
    private String price;
    private String warehouseUnit;
    private String warrantyInfor;
    private String generalInfor;
    private String screen;
    private String operatingSystem;
    private String cpu;
    private String ram;
    private String design;
    private int batteryCapacity_mAh;
    private long categoryID;
    private long manufacturerID;
    private MultipartFile image;
}
