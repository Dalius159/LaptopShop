package Website.LaptopShop.Validator;

import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Services.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class NguoiDungValidator implements Validator {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Override
    public boolean supports(Class<?> clazz) {return NguoiDung.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {

        NguoiDung user = (NguoiDung) target;

        ValidationUtils.rejectIfEmpty(errors, "email", "error.hoTen", "Họ tên không được bỏ trống");
        ValidationUtils.rejectIfEmpty(errors, "soDienThoai", "error.soDienThoai", "Số điện thoại không được bỏ trống");
        ValidationUtils.rejectIfEmpty(errors, "diaChi", "error.diaChi", "Địa chỉ không được bỏ trống");

        // validate cho email
        // check ko đc trống
        ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email không được trống");

        // check địa chỉ email phù hợp hay không
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (!(pattern.matcher(user.getEmail()).matches())) {
            errors.rejectValue("email", "error.email", "Địa chỉ email không phù hợp");
        }

        // check địa chi email đã được dùng chưa
        if (nguoiDungService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.email", "Địa chỉ email đã được sử dụng");
        }

        // check password trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password không được bỏ trống");

        // check confirmPassword trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword",
                "Nhắc lại mật khẩu không được bỏ trống");

        // check độ dài password (8-32)
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "error.password", "Mật khẩu phải dài 8-32 ký tự");
        }

        // check match pass và confirmPass
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Nhắc lại mật khẩu không chính xác");
        }
    }
}
