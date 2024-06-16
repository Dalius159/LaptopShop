package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.AccountDTO;
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
@RequestMapping("/api/account")
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
	public ResponseObject saveAccount(@RequestBody @Valid AccountDTO dto, BindingResult result, Model model) {
		ResponseObject responseObject = new ResponseObject();

		validateAccount(dto, result);

		if (result.hasErrors()) {
			setErrorsForResponseObject(result, responseObject);
		} else {
			responseObject.setStatus("success");
			userService.saveUserForAdmin(dto);
		}

		return responseObject;
	}

	@DeleteMapping("/delete/{id}")
	public void deleteAccount(@PathVariable long id) {
		userService.deleteById(id);
	}

	private void validateAccount(AccountDTO dto, BindingResult result) {
		if (userService.findByEmail(dto.getEmail()) != null) {
			result.rejectValue("email", "error.email", "Email already used!");
		}
		if (dto.getConfirmPassword() == null || dto.getPassword() == null || !dto.getConfirmPassword().equals(dto.getPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "Wrong confirm password!");
		}
	}

	private void setErrorsForResponseObject(BindingResult result, ResponseObject object) {
		Map<String, String> errors = result.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		object.setErrorMessages(errors);
		object.setStatus("fail");
	}
}
