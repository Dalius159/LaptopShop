package Website.LaptopShop.Validator;

import Website.LaptopShop.DTO.SanPhamDTO;
import Website.LaptopShop.Services.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SanPhamDtoValidator implements Validator {
    @Autowired
    private DanhMucService dmService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SanPhamDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SanPhamDTO s = (SanPhamDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "tenSanPham", "error.tenSanPham", "Tên sản phẩm không được trống");
        ValidationUtils.rejectIfEmpty(errors, "donGia", "error.donGia", "Đơn giá không được trống");
        ValidationUtils.rejectIfEmpty(errors, "donViKho", "error.donViKho", "Đơn vị kho không được trống");
        ValidationUtils.rejectIfEmpty(errors, "thongTinBaoHanh", "error.thongTinBaoHanh", "Thông tin bảo hành không được trống");
        ValidationUtils.rejectIfEmpty(errors, "thongTinChung", "error.thongTinChung", "Thông tin chung không được trống");

        if(Integer.parseInt(s.getDonGia()) < 0) {
            errors.rejectValue("donGia", "error.donGia", "Đơn giá không được âm");
        }

        if(Integer.parseInt(s.getDonViKho()) < 0) {
            errors.rejectValue("donViKho", "error.donViKho", "Đơn giá không được âm");
        }
        String tenDanhMuc = dmService.getDanhMucById(s.getDanhMucId()).getTenDanhMuc().toLowerCase();

        if(tenDanhMuc.contains("Laptop".toLowerCase())) {
            ValidationUtils.rejectIfEmpty(errors, "manHinh", "error.manHinh", "Màn hình không được trống");
            ValidationUtils.rejectIfEmpty(errors, "heDieuHanh", "error.heDieuHanh", "Hệ điều hành không được trống");
            ValidationUtils.rejectIfEmpty(errors, "cpu", "error.cpu", "CPU không được trống");
            ValidationUtils.rejectIfEmpty(errors, "ram", "error.ram", "Ram không được trống");
            ValidationUtils.rejectIfEmpty(errors, "thietKe", "error.thietKe", "Thiết kế không được trống");
            ValidationUtils.rejectIfEmpty(errors, "dungLuongPin", "error.dungLuongPin", "Dung lượng pin được trống");
        }

    }
}
