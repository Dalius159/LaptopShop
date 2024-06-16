package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.PasswordDTO;
import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/profile")
public class ProfileAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/{id}")
	public Users getUserById(@PathVariable long id) {
		return userService.findById(id);
	}

	@PostMapping("/changePassword")
	public ResponseObject changePass(@RequestBody @Valid PasswordDTO dto, BindingResult result,
									 HttpServletRequest request) {
		System.out.println(dto.toString());
		Users currentUser = getSessionUser(request);

		ResponseObject ro = new ResponseObject();
		
		if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
			result.rejectValue("oldPassword", "error.oldPassword", "Wrong old password!");
		}

		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
			result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Wrong confirm password!");
		}

		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
		    List<FieldError> errorsList = result.getFieldErrors();
		    for (FieldError error : errorsList ) {
		        errors.put(error.getField(), error.getDefaultMessage());
		    }
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			userService.changePass(currentUser, dto.getNewPassword());
			ro.setStatus("success");
		}
		
		return ro;
	}

	public Users getSessionUser(HttpServletRequest request) {
		return (Users) request.getSession().getAttribute("loggedInUser");
	}
}
