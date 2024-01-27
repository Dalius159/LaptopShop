package Website.LaptopShop.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
@Data
public class LienHeDTO {
    private long id;

    @NotEmpty(message="This field can't be empty!")
    private String noiDungTraLoi;

    private String tieuDe;
    private String diaChiDen;

    private Date ngayTraLoi;
}
