package Website.LaptopShop.Controller;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.SearchProductObject;
import Website.LaptopShop.Entities.*;
import Website.LaptopShop.Services.*;
import Website.LaptopShop.Validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")
public class ClientController {
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private SecurityService securityService;

	@ModelAttribute("loggedInUser")
	public Users loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}

	@ModelAttribute("categoryList")
	public List<Category> categoryList() {
		return categoryService.getAllCategory();
	}

	@GetMapping
	public String clientPage(Model model, HttpServletRequest request) {
		model.addAttribute("request1", request);
//		model.addAttribute("test", "vip");
		return "client/home";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("newUser", new Users());
		return "client/login";
	}

	@GetMapping("/contact")
	public String contactPage() {
		return "client/contact";
	}

	@PostMapping("/createContact")
	@ResponseBody
	public ResponseObject createContact(@RequestBody Contact lh) {
		lh.setContactDate(new Date());
		contactService.save(lh);
		return new ResponseObject();
	}

	@GetMapping("/store")
	public String storePage(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String range,
			@RequestParam(defaultValue = "") String category,
			@RequestParam(defaultValue = "") String manufacturer,
			@RequestParam(defaultValue = "") String os,
			@RequestParam(defaultValue = "") String ram,
			@RequestParam(defaultValue = "0") int battery,
			Model model) {
		SearchProductObject obj = new SearchProductObject();
		obj.setCategory(category);
		obj.setPrice(range);
		obj.setManufacturer(manufacturer);
		obj.setOperatingSystem(os);
		obj.setRam(ram);
		obj.setBatteryCapacity_mAh(battery);
		Page<Product> list = productService.getProductByCategory(obj, page, 12);
		int totalPage = list.getTotalPages();
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("range", range);
		model.addAttribute("category", category);
		model.addAttribute("manufacturer", manufacturer);
		model.addAttribute("os", os);
		model.addAttribute("ram", ram);
		model.addAttribute("battery", battery);
		List<Integer> pagelist = new ArrayList<>();

		//Get page list
		if (page >= 1 && page < 4) {
			for (int i = 2; i <= 5 && i <= totalPage; i++) {
				pagelist.add(i);
			}
		} else if (page == totalPage) {
			for (int i = totalPage; i >= totalPage - 3 && i > 1; i--) {
				pagelist.add(i);
			}
			Collections.sort(pagelist);
		} else {
			for (int i = page; i <= page + 2 && i <= totalPage; i++) {
				pagelist.add(i);
			}
			for (int i = page - 1; i >= page - 2 && i > 1; i--) {
				pagelist.add(i);
			}
			Collections.sort(pagelist);
		}
		model.addAttribute("pageList", pagelist);

		//Get category and manufacturer found
		Set<String> manufacturer2 = new HashSet<>();
		Set<Integer> pinSet = new HashSet<>();
		Iterable<Product> products = productService.getProductByCategoryName(category);
		for (Product product : products) {
			manufacturer2.add(product.getManufacturer().getManufacturerName());
			if (category.equals("Laptop")) {
				pinSet.add(product.getBatteryCapacity_mAh());
			}
		}
		model.addAttribute("manufacturer", manufacturer2);
		model.addAttribute("pinSet", pinSet);
		return "client/store";
	}

	@Value("${disqus.seed}")
	private String disqusSeed;

	@GetMapping("/sp")
	public String productDetail(@RequestParam int id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		model.addAttribute("disqusSeed", disqusSeed);
		return "client/productDetail";
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping("/guarantee")
	public String guaranteePage(Model model) {

		return "client/guarantee";
	}

	@PostMapping("/register")
	public String registerProcess(@ModelAttribute("newUser") @Valid Users users, BindingResult bindingResult) {

		userValidator.validate(users, bindingResult);

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((e) -> System.err.println(e.getDefaultMessage()));
			return "client/login";
		}
		userService.saveUserForMember(users);

		securityService.autologin(users.getEmail(), users.getConfirmPassword());

		return "redirect:/login?success";
	}
}
