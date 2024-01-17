package Website.LaptopShop.API;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.HangSanXuat;
import Website.LaptopShop.Services.HangSanXuatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/nhan-hieu")
public class HangSXAPI {

	@Autowired
	private HangSanXuatService service;

	@GetMapping("/all")
	public Page<HangSanXuat> getAllHangSanXuat(@RequestParam(defaultValue = "1") int page) {
		return service.getALlHangSX(page-1,6);
	}

	@GetMapping("/{id}")
	public HangSanXuat getHangSanXuatById(@PathVariable long id) {
		return service.getHSXById(id);
	}

	@PostMapping(value = "/save")
	public ResponseObject addHangSanXuat(@RequestBody @Valid HangSanXuat newHangSanXuat, BindingResult result) {

		ResponseObject ro = new ResponseObject();

		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
		} else {
			service.save(newHangSanXuat);
			ro.setData(newHangSanXuat);
			ro.setStatus("success");
		}
		return ro;
	}

	@PutMapping(value = "/update")
	public ResponseObject updateHangSanXuat(@RequestBody @Valid HangSanXuat editHangSanXuat, BindingResult result) {

		ResponseObject ro = new ResponseObject();
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			service.save(editHangSanXuat);
			ro.setData(editHangSanXuat);
			ro.setStatus("success");
		}

		return ro;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteHangSanXuat(@PathVariable long id) {
		service.deleteById(id);
		return "OK !";
	}
}
