package Website.LaptopShop.Controller;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.SearchSanPhamObject;
import Website.LaptopShop.Entities.*;
import Website.LaptopShop.Services.*;
import Website.LaptopShop.Validator.NguoiDungValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
	private SanPhamService sanPhamService;

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private DanhMucService danhMucService;

	@Autowired
	private LienHeService lienHeService;

	@Autowired
	private NguoiDungValidator nguoiDungValidator;

	@Autowired
	private SecurityService securityService;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return nguoiDungService.findByEmail(auth.getName());
	}

	@ModelAttribute("listDanhMuc")
	public List<DanhMuc> listDanhMuc() {
		return danhMucService.getAllDanhMuc();
	}

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}

	@GetMapping
	public String clientPage(Model model, HttpServletRequest request) {
		model.addAttribute("request1", request);
		model.addAttribute("test", "vip");
		return "client/home";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("newUser", new NguoiDung());
		return "client/login";
	}

	@GetMapping("/contact")
	public String contactPage() {
		return "client/contact";
	}

	@PostMapping("/createContact")
	@ResponseBody
	public ResponseObject createContact(@RequestBody LienHe lh) {
		lh.setNgayLienHe(new Date());
		lienHeService.save(lh);
		return new ResponseObject();
	}

	@GetMapping("/store")
	public String storePage(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String range,
			@RequestParam(defaultValue = "") String brand,
			@RequestParam(defaultValue = "") String manufactor,
			@RequestParam(defaultValue = "") String os,
			@RequestParam(defaultValue = "") String ram,
			@RequestParam(defaultValue = "") int pin,
			Model model) {
		SearchSanPhamObject obj = new SearchSanPhamObject();
		obj.setBrand(brand);
		obj.setDonGia(range);
		obj.setManufactor(manufactor);
		obj.setOs(os);
		obj.setRam(ram);
		obj.setPin(pin);
		Page<SanPham> list = sanPhamService.getSanPhamByBrand(obj, page, 12);
		int totalPage = list.getTotalPages();
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("range", range);
		model.addAttribute("brand", brand);
		model.addAttribute("manufactor", manufactor);
		model.addAttribute("os", os);
		model.addAttribute("ram", ram);
		model.addAttribute("pin", pin);
		List<Integer> pagelist = new ArrayList<>();

		//Lap ra danh sach cac trang
		if (page == 1 || page == 2 || page == 3 || page == 4) {
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

		//Lay cac danh muc va hang san xuat tim thay
		Set<String> hangsx = new HashSet<String>();
		Set<Integer> pinSet = new HashSet<Integer>();
		Iterable<SanPham> dum = sanPhamService.getSanPhamByTenDanhMuc(brand);
		for (SanPham sp : dum) {
			hangsx.add(sp.getHangSanXuat().getTenHangSanXuat());
			if (brand.equals("Laptop")) {
				pinSet.add(sp.getDungLuongPin_mAh());
			}
		}
		model.addAttribute("hangsx", hangsx);
		model.addAttribute("pinSet", pinSet);
		return "client/store";
	}

	@GetMapping("/sp")
	public String detailspPage(@RequestParam int id, Model model) {
		SanPham sp = sanPhamService.getSanPhamById(id);
		model.addAttribute("sp", sp);
		return "client/detailsp";
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
	public String registerProcess(@ModelAttribute("newUser") @Valid NguoiDung nguoiDung, BindingResult bindingResult, Model model) {

		nguoiDungValidator.validate(nguoiDung, bindingResult);

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((e) -> {System.err.println(e.getDefaultMessage());});
			return "client/login";
		}

		nguoiDungService.saveUserForMember(nguoiDung);

		securityService.autologin(nguoiDung.getEmail(), nguoiDung.getConfirmPassword());

		return "redirect:/login?success";
	}
}
