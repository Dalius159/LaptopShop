package Website.LaptopShop.Controller;

import Website.LaptopShop.Entities.ChiMucGioHang;
import Website.LaptopShop.Entities.GioHang;
import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Entities.SanPham;
import Website.LaptopShop.Services.ChiMucGioHangService;
import Website.LaptopShop.Services.GioHangService;
import Website.LaptopShop.Services.NguoiDungService;
import Website.LaptopShop.Services.SanPhamService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;

@Controller
@SessionAttributes("loggedInUser")
public class CartController {
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private GioHangService gioHangService;
	@Autowired
	private ChiMucGioHangService chiMucGioHangService;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return nguoiDungService.findByEmail(auth.getName());
	}

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}

	@GetMapping("/cart")
	public String cartPage(HttpServletRequest req, HttpServletResponse res, Model model) {
		NguoiDung currentUser = getSessionUser(req);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<Long, Long> quantity = new HashMap<>();
		List<SanPham> listsp = new ArrayList<>();
		Cookie[] cl = req.getCookies();
		//Lay tu cookie
		if (auth == null || auth.getPrincipal() == "anonymousUser") {

			Set<Long> idList = new HashSet<>();
			for (Cookie cookie : cl) {
				if (cookie.getName().matches("[0-9]+")) {
					idList.add(Long.parseLong(cookie.getName()));
					quantity.put(Long.parseLong(cookie.getName()), Long.parseLong(cookie.getValue()));
				}

			}
			listsp = sanPhamService.getAllSanPhamByList(idList);
			//Lay tu database
		} else {
			GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
			if (g == null) {
				g = new GioHang();
				g.setNguoiDung(currentUser);
				gioHangService.save(g);
			}
			List<ChiMucGioHang> listchimuc = chiMucGioHangService.getChiMucGioHangByGioHang(g);
			ChiMucGioHang chiMucGioHang;
			int flag;
			var soLuong = 1;
			for (Cookie cookie : cl) {
				flag = 0;
				if (cookie.getName().matches("[0-9]+")) {
					long id = Long.parseLong(cookie.getName());
					soLuong = Integer.parseInt(cookie.getValue());
					for (ChiMucGioHang mucGioHang : listchimuc) {
						chiMucGioHang = mucGioHang;
						if (id == chiMucGioHang.getSanPham().getId()) {
							chiMucGioHang.setSoLuong(chiMucGioHang.getSoLuong() + soLuong);
							flag = 1;
							break;
						}

						chiMucGioHangService.saveChiMucGiohang(chiMucGioHang);
					}

					if (flag == 0) {
						chiMucGioHang = new ChiMucGioHang();
						chiMucGioHang.setGioHang(g);
						chiMucGioHang.setSanPham(sanPhamService.getSanPhamById(id));
						chiMucGioHang.setSoLuong(soLuong);
						listchimuc.add(chiMucGioHang);

						chiMucGioHangService.saveChiMucGiohang(chiMucGioHang);
					}
				}
			}

			for (ChiMucGioHang c : listchimuc) {
				listsp.add(c.getSanPham());
				quantity.put(c.getSanPham().getId(), (long) c.getSoLuong());
			}

			ClearUpRightBeforeCheckout(req, res);
		}

		model.addAttribute("checkEmpty", listsp.size());
		model.addAttribute("cart", listsp);
		model.addAttribute("quantity", quantity);


		return "client/cart";
	}

	public void ClearUpRightBeforeCheckout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] clientCookies = request.getCookies();
		for (Cookie clientCookie : clientCookies) {
			if (clientCookie.getName().matches("[0-9]+")) {
				clientCookie.setMaxAge(0);
				clientCookie.setPath("/");
				response.addCookie(clientCookie);
			}
		}
	}
}
