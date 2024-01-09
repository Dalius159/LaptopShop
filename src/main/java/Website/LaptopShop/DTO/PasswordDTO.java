package Website.LaptopShop.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class PasswordDTO {
    @NotEmpty(message = "Phải nhập mật khẩu cũ")
    private String oldPassword;

    @NotEmpty(message = "Phải nhập mật khẩu mới")
    @Length(min=8, max=32, message="Mật khẩu phải dài 8-32 ký tự")
    private String newPassword;

    @NotEmpty(message = "Phải nhắc lại mật khẩu mới")
    private String confirmNewPassword;
}
