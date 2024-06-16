package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.Manufacturer;
import Website.LaptopShop.Services.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerAPI {

	@Autowired
	private ManufacturerService service;

	@GetMapping("/all")
	public Page<Manufacturer> getAllManufacturer(@RequestParam(defaultValue = "1") int page) {
		return service.getALlManufacturer(page-1,9);
	}

	@GetMapping("/{id}")
	public Manufacturer getManufacturerById(@PathVariable long id) {
		return service.getManufacturerById(id);
	}

	@PostMapping(value = "/save")
	public ResponseObject addManufacturer(@RequestBody @Valid Manufacturer newManufacturer, BindingResult result) {

		ResponseObject ro = new ResponseObject();

		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
		} else {
			service.save(newManufacturer);
			ro.setData(newManufacturer);
			ro.setStatus("success");
		}
		return ro;
	}

	@PutMapping(value = "/update")
	public ResponseObject updateManufacturer(@RequestBody @Valid Manufacturer editManufacturer, BindingResult result) {

		ResponseObject ro = new ResponseObject();
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			service.save(editManufacturer);
			ro.setData(editManufacturer);
			ro.setStatus("success");
		}

		return ro;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteManufacturer(@PathVariable long id) {
		service.deleteById(id);
		return "OK !";
	}
}
