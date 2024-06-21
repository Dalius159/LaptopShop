package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ContactDTO;
import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.SearchContactObject;
import Website.LaptopShop.Entities.Contact;
import Website.LaptopShop.Services.ContactService;
import Website.LaptopShop.Ultilities.EmailUlti;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/contact")
public class ContactAPI {

	@Autowired
	private ContactService contactService;

	@Autowired
	private EmailUlti emailUlti;

	@GetMapping("/all")
	public Page<Contact> getContactByFilter(@RequestParam(defaultValue = "1") int page,
											@RequestParam String contactStatus, @RequestParam String fromDate, @RequestParam String toDate)
			throws ParseException {
		SearchContactObject object = new SearchContactObject();
		object.setToDate(toDate);
		object.setContactStatus(contactStatus);
		object.setFromDate(fromDate);

		return contactService.getContactByFilter(object, page);
	}

	@GetMapping("/{id}")
	public Contact getContactById(@PathVariable long id) {
		return contactService.findById(id);
	}

	@PostMapping("/reply")
	public ResponseObject respondContactProcess(@RequestBody @Valid ContactDTO dto, BindingResult result) {
		ResponseObject ro = new ResponseObject();

		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
		} else {
			// Kiểm tra email không được null hoặc rỗng
			if (dto.getToEmail() == null || dto.getToEmail().isEmpty()) {
				ro.setStatus("fail");
				ro.setErrorMessages(Map.of("toEmail", "Email address must not be null or empty"));
				return ro;
			}

			emailUlti.sendEmail(dto.getToEmail(), dto.getTitle(), dto.getRespondMessage());

			Contact contact = contactService.findById(dto.getId());
			contact.setStatus("Responded");
			contact.setRespondDate(new Date());
			contact.setRespondMessage(dto.getRespondMessage());

			contactService.save(contact);
			ro.setStatus("success");
		}
		return ro;
	}
}
