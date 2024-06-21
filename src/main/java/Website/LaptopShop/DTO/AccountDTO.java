package Website.LaptopShop.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotEmpty;
@Data
public class AccountDTO {
    private long id;

    @NotEmpty(message="Enter an email address!")
    @Email(message= "Wrong email!")
    private String email;

    @Length(min=8, max=32, message="Password must be 8-32 characters")
    private String password;

    private String confirmPassword;

    @NotEmpty(message="Enter an adress!")
    private String address;

    @NotEmpty(message="Enter your Fullname!")
    private String fullName;

    @NotEmpty(message="Enter a phone number!")
    private String phoneNumber;
    private String roleName;
}
