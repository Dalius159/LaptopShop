package Website.LaptopShop.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class PasswordDTO {
    @NotEmpty(message = "Enter old Password!")
    private String oldPassword;

    @NotEmpty(message = "Enter new Password!")
    @Length(min=8, max=32, message="Password must contain 8-32 characters!")
    private String newPassword;

    @NotEmpty(message = "Confirm new Password!")
    private String confirmNewPassword;
}
