package Website.LaptopShop.API;

import Website.LaptopShop.DTO.ResponseObject;
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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/gio-hang")
@SessionAttributes("loggedInUser")
public class GioHangAPI {

	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private GioHangService gioHangService;
	@Autowired
	private SanPhamService sanPhamService;
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

	@GetMapping("/addSanPham")
	public ResponseObject addToCart(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		ResponseObject ro = new ResponseObject();
		SanPham sp = sanPhamService.getSanPhamById(Long.parseLong(id));
		if (sp.getDonViKho() == 0) {
			ro.setStatus("false");
			return ro;
		}
		NguoiDung currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Su dung cookie de luu
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			boolean found = false;
			for (int i = 0; i < clientCookies.length; i++) {
				//Neu san pham da co trong cookie tang so luong them 1
				if (clientCookies[i].getName().equals(id)) {
					clientCookies[i].setValue(Integer.toString(Integer.parseInt(clientCookies[i].getValue()) + 1));
					clientCookies[i].setPath("/");
					clientCookies[i].setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(clientCookies[i]);
					found = true;
					break;
				}
			}
			//Neu san pham ko co trong cookie,them vao cookie
			if (!found) {
				Cookie c = new Cookie(id, "1");
				c.setPath("/");
				c.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(c);
			}
			//Su dung database de luu
		} else {
			GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
			if (g == null) {
				g = new GioHang();
				g.setNguoiDung(currentUser);
				g = gioHangService.save(g);
			}

			ChiMucGioHang c = chiMucGioHangService.getChiMucGioHangBySanPhamAndGioHang(sp, g);
			//Neu khong tim chi muc gio hang, tao moi
			if (c == null) {
				System.out.println(g.getNguoiDung().getEmail());
				System.out.println(g.getId());
				c = new ChiMucGioHang();
				c.setGioHang(g);
				c.setSanPham(sp);
				c.setSoLuong(1);
				//Neu san pham da co trong database tang so luong them 1
			} else {
				c.setSoLuong(c.getSoLuong() + 1);
			}
			c = chiMucGioHangService.saveChiMucGiohang(c);
		}
		ro.setStatus("success");
		return ro;
	}

	@GetMapping("/changSanPhamQuanity")
	public ResponseObject changeQuanity(@RequestParam String id, @RequestParam String value, HttpServletRequest request, HttpServletResponse response) {
		NguoiDung currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ResponseObject ro = new ResponseObject();
		//Su dung cookie de luu
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			for (int i = 0; i < clientCookies.length; i++) {
				if (clientCookies[i].getName().equals(id)) {
					clientCookies[i].setValue(value);
					clientCookies[i].setPath("/");
					clientCookies[i].setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(clientCookies[i]);
					break;
				}
			}
			//Su dung database de luu
		} else {
			GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
			SanPham sp = sanPhamService.getSanPhamById(Long.parseLong(id));
			ChiMucGioHang c = chiMucGioHangService.getChiMucGioHangBySanPhamAndGioHang(sp, g);
			c.setSoLuong(Integer.parseInt(value));
			c = chiMucGioHangService.saveChiMucGiohang(c);
		}
		ro.setStatus("success");
		return ro;
	}

	@GetMapping("/deleteFromCart")
	public ResponseObject deleteSanPham(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		NguoiDung currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ResponseObject ro = new ResponseObject();
		//Su dung cookie de luu
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			for (int i = 0; i < clientCookies.length; i++) {
				if (clientCookies[i].getName().equals(id)) {
					clientCookies[i].setMaxAge(0);
					clientCookies[i].setPath("/");
					System.out.println(clientCookies[i].getMaxAge());
					response.addCookie(clientCookies[i]);
					break;
				}
			}
			//Su dung database de luu
		} else {
			GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
			SanPham sp = sanPhamService.getSanPhamById(Long.parseLong(id));
			ChiMucGioHang c = chiMucGioHangService.getChiMucGioHangBySanPhamAndGioHang(sp, g);
			chiMucGioHangService.deleteChiMucGiohang(c);
		}

		ro.setStatus("success");
		return ro;
	}
}
