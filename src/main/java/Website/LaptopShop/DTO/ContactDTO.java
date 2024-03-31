package Website.LaptopShop.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
@Data
public class ContactDTO {
    private long id;

    @NotEmpty(message="This field can't be empty!")
    private String respondMessage;

    private String title;
    private String toEmail;

    private Date respondDate;
}
