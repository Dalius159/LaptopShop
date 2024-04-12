package Website.LaptopShop.Controller;

import Website.LaptopShop.DTO.SearchProductObject;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.UserService;
import Website.LaptopShop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;

@Controller
@SessionAttributes("loggedInUser")
public class ClientSearchController {
	@Autowired
	private ProductService productService;


//	TODO: loggedInUser() function need to be declared in any controller using loggedInUser, need refactor
	private final UserService userService;

	@Autowired
	public ClientSearchController(UserService userService){
		this.userService = userService;
	}
	@ModelAttribute("loggedInUser")
	public Users loggedInUser() {
		return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@GetMapping("search")
	public String searchSP(@RequestParam(defaultValue = "1") int page,
						   @RequestParam String name,
						   @RequestParam(defaultValue = "") String sort,
						   @RequestParam(defaultValue = "") String range,
						   @RequestParam(defaultValue = "") String category,
						   @RequestParam(defaultValue = "") String manufacturer,
						   Model model) {
		SearchProductObject obj = new SearchProductObject();
		obj.setKeyword(name.split(" "));
		obj.setSort(sort);
		obj.setPrice(range);
		obj.setCategory(category);
		obj.setManufacturer(manufacturer);
		Page<Product> list = productService.getProductByProductName(obj, page, 12);
		int totalPage = list.getTotalPages();
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("name", name);
		model.addAttribute("sort", sort);
		model.addAttribute("range", range);
		model.addAttribute("brand", category);
		model.addAttribute("manufacturer", manufacturer);
		List<Integer> pagelist = new ArrayList<>();

		// Page list, need refactoring
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

		//Get categories and manufacturers found
		Set<String> category2 = new HashSet<>();
		Set<String> manufacturer2 = new HashSet<>();
		Iterable<Product> dum = productService.getProductByProductNameWithoutPaginate(obj);
		for (Product sp : dum) {
			category2.add(sp.getCategory().getCategoryName());
			manufacturer2.add(sp.getManufacturer().getManufacturerName());
		}
		model.addAttribute("category", category2);
		model.addAttribute("manufacturer2", manufacturer2);

		return "client/searchResult";
	}
}
