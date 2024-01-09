package Website.LaptopShop.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
@Data
public class LienHeDTO {
    private long id;

    @NotEmpty(message="Nội dung trả lời không được trống")
    private String noiDungTraLoi;

    private String tieuDe;
    private String diaChiDen;

    private Date ngayTraLoi;
}
