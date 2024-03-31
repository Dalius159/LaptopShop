package Website.LaptopShop.Validator;

import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {return Users.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {

        Users user = (Users) target;

        ValidationUtils.rejectIfEmpty(errors, "email", "error.fullName", "Fullname can't be empty");
        //ValidationUtils.rejectIfEmpty(errors, "soDienThoai", "error.soDienThoai", "Phone number can't be empty");
        //ValidationUtils.rejectIfEmpty(errors, "diaChi", "error.diaChi", "Address can't be empty");

        // validate for email check for empty
        ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email can't be empty");

        // check email if it's proper or not
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (!(pattern.matcher(user.getEmail()).matches())) {
            errors.rejectValue("email", "error.email", "Email not match");
        }

        // check email if it's used or not
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.email", "Email already used");
        }

        // check password if it be left empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Can't be emty");

        // check confirmPassword empty or not
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword",
                "Can't be emty");

        // check length password (8-32)
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "error.password", "Length required: [8-32]");
        }

        // check match pass and confirmPass
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Wrong Confirm Password");
        }
    }
}
