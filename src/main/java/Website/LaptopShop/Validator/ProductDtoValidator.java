package Website.LaptopShop.Validator;

import Website.LaptopShop.DTO.ProductDTO;
import Website.LaptopShop.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductDtoValidator implements Validator {
    @Autowired
    private CategoryService dmService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductDTO s = (ProductDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "productName", "error.productName", " enter product name!");
        ValidationUtils.rejectIfEmpty(errors, "price", "error.price", "enter price!");
        ValidationUtils.rejectIfEmpty(errors, "warehouseUnit", "error.warehouseUnit", "enter warehouse unit");
        ValidationUtils.rejectIfEmpty(errors, "warrantyInfor", "error.warrantyInfor", " enter warranty information!");
        ValidationUtils.rejectIfEmpty(errors, "generalInfor", "error.generalInfor", "enter general information!");

        if(Integer.parseInt(s.getPrice()) < 0) {
            errors.rejectValue("price", "error.price", "price can't be negative");
        }

        if(Integer.parseInt(s.getWarehouseUnit()) < 0) {
            errors.rejectValue("warehouseUnit", "error.warehouseUnit", "warehouse unit can't be negative");
        }
        String tenDanhMuc = dmService.getCategoryById(s.getCategoryID()).getCategoryName().toLowerCase();

        if(tenDanhMuc.contains("Laptop".toLowerCase())) {
            ValidationUtils.rejectIfEmpty(errors, "screen", "error.screen", "enter screen!");
            ValidationUtils.rejectIfEmpty(errors, "operatingSystem", "error.operatingSystem", "enter operating system!");
            ValidationUtils.rejectIfEmpty(errors, "cpu", "error.cpu", "enter CPU!");
            ValidationUtils.rejectIfEmpty(errors, "ram", "error.ram", "enter Ram");
            ValidationUtils.rejectIfEmpty(errors, "design", "error.design", "enter design!");
            ValidationUtils.rejectIfEmpty(errors, "batteryCapacity_mAh", "error.batteryCapacity_mAh", "enter batteryCapacity_mAh!");
        }

    }
}
