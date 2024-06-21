package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.Category;
import Website.LaptopShop.Services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/category")
public class CategoryAPI {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/all")
	public Page<Category> getAllCategory(@RequestParam(defaultValue = "1") int page) {
		return categoryService.getAllCategoryForPageable(page - 1, 9);
	}

	@GetMapping("/allForReal")
	public List<Category> getRealAllCategory() {
		return categoryService.getAllCategory();
	}

	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable long id) {
		return categoryService.getCategoryById(id);
	}

	@PostMapping(value = "/save")
	public ResponseObject addCategory(@RequestBody @Valid Category newCategory, BindingResult result, HttpServletRequest request) {

		ResponseObject ro = new ResponseObject();

		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);

			List<String> keys = new ArrayList<>(errors.keySet());
			for (String key : keys) {
				System.out.println(key + ": " + errors.get(key));
			}

			ro.setStatus("fail");
			errors = null;
		} else {
			categoryService.save(newCategory);
			ro.setData(newCategory);
			ro.setStatus("success");
		}
		return ro;
	}

	@PutMapping(value = "/update")
	public ResponseObject updateCategory(@RequestBody @Valid Category editCategory, BindingResult result, HttpServletRequest request) {

		ResponseObject ro = new ResponseObject();
		if (result.hasErrors()) {

			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;

		} else {
			categoryService.save(editCategory);
			ro.setData(editCategory);
			ro.setStatus("success");
		}

		return ro;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCategory(@PathVariable long id, HttpServletRequest request) {
		categoryService.deleteById(id);
		request.getSession().setAttribute("categoryList", categoryService.getAllCategory());
		return "OK !";
	}

}
