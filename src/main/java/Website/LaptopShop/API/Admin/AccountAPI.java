package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.AcountDTO;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Roles;
import Website.LaptopShop.Services.UserService;
import Website.LaptopShop.Services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/tai-khoan")
public class AccountAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@GetMapping("/all")
	public Page<Users> getUserByRole(@RequestParam("roleName") String roleName,
                                            @RequestParam(defaultValue = "1") int page) {
		Set<Roles> roles = new HashSet<>();
		roles.add(roleService.findByRoleName(roleName));

		return userService.getUserByRole(roles, page);
	}


	@PostMapping("/save")
	public ResponseObject saveAcount(@RequestBody @Valid AcountDTO dto, BindingResult result, Model model) {
		
		ResponseObject ro = new ResponseObject();

		if(userService.findByEmail(dto.getEmail()) != null) {
			result.rejectValue("email", "error.email","Email already used!");
		}
		if(!dto.getConfirmPassword().equals(dto.getPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword","wrong confirm password!");
		}

		if (result.hasErrors()) {
			setErrorsForResponseObject(result, ro);
		} else {
			ro.setStatus("success");
			userService.saveUserForAdmin(dto);
		}	
		return ro;
	}

	@DeleteMapping("/delete/{id}")
	public void deleteAcount(@PathVariable long id) {
		userService.deleteById(id);
	}
	public void setErrorsForResponseObject(BindingResult result, ResponseObject object) {

		Map<String, String> errors = result.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		object.setErrorMessages(errors);
		object.setStatus("fail");
		
		List<String> keys = new ArrayList<String>(errors.keySet());			
		for (String key: keys) {
		    System.out.println(key + ": " + errors.get(key));
		}
		
		errors = null;
	}
}
