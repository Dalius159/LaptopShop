package Website.LaptopShop.Controller;

import Website.LaptopShop.Entities.*;
import Website.LaptopShop.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@SessionAttributes("loggedInUser")
public class CheckOutController {
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private GioHangService gioHangService;
	@Autowired
	private ChiMucGioHangService chiMucGioHangService;
	@Autowired
	private DonHangService donHangService;
	@Autowired
	private ChiTietDonHangService chiTietDonHangService;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return nguoiDungService.findByEmail(auth.getName());
	}

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}

	@GetMapping("/checkout")
	public String checkoutPage(HttpServletRequest req, Model model) {
		NguoiDung currentUser = getSessionUser(req);
		Map<Long, String> quantity = new HashMap<>();
		List<SanPham> listsp = new ArrayList<>();

		GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);

		List<ChiMucGioHang> listchimuc = chiMucGioHangService.getChiMucGioHangByGioHang(g);

		for (ChiMucGioHang c : listchimuc) {
			listsp.add(c.getSanPham());
			quantity.put(c.getSanPham().getId(), Integer.toString(c.getSoLuong()));
		}

//      TODO: fix typo
		model.addAttribute("cart", listsp);
		model.addAttribute("quanity", quantity);
		model.addAttribute("user", currentUser);
		model.addAttribute("donhang", new DonHang());

		return "client/checkout";
	}

	@PostMapping("/complete-order")
	public String completeOrder(@ModelAttribute("donhang") DonHang donhang, HttpServletRequest req, HttpServletResponse response, Model model) {
		SaveOrder(donhang, req, response, model, (byte) 0);

		return "redirect:/thankyou";
	}

	@GetMapping(value = "/thankyou")
	public String thankyouPage(HttpServletRequest req, Model model) {
		NguoiDung currentUser = getSessionUser(req);
		DonHang donhang = donHangService.findLatestDonHangByMaNguoiDat(currentUser.getId());
		Map<Long, Long> quanity = new HashMap<>();
		List<SanPham> listsp = new ArrayList<SanPham>();

		List<ChiTietDonHang> chiTietDonHangs = donhang.getDanhSachChiTiet();
		for (ChiTietDonHang c : chiTietDonHangs) {
			listsp.add(c.getSanPham());
			quanity.put(c.getSanPham().getId(), (long) c.getSoLuongDat());
		}

		model.addAttribute("donhang", donhang);
		model.addAttribute("cart", listsp);
		model.addAttribute("quanity", quanity);

		return "client/thank-you";
	}

	public void SaveOrder(DonHang donhang, HttpServletRequest req, HttpServletResponse response, Model model, byte status) {
		if (status == 1) {donhang.setGhiChu("Đã thanh toán");} else {donhang.setGhiChu("Thanh toán khi nhận hàng");}
		donhang.setNgayDatHang(new Date());
		donhang.setTrangThaiDonHang("Đang chờ giao");

		NguoiDung currentUser = getSessionUser(req);
		Map<Long, String> quanity = new HashMap<Long, String>();
		List<SanPham> listsp = new ArrayList<SanPham>();
		List<ChiTietDonHang> listDetailDH = new ArrayList<ChiTietDonHang>();

		donhang.setNguoiDat(currentUser);
		System.out.println(donhang.getId());
		DonHang d = donHangService.save(donhang);
		GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
		List<ChiMucGioHang> listchimuc = chiMucGioHangService.getChiMucGioHangByGioHang(g);
		for (ChiMucGioHang c : listchimuc) {
			ChiTietDonHang detailDH = new ChiTietDonHang();
			detailDH.setSanPham(c.getSanPham());
			detailDH.setDonGia(c.getSoLuong() * c.getSanPham().getDonGia());
			detailDH.setSoLuongDat(c.getSoLuong());
			detailDH.setDonHang(d);
			listDetailDH.add(detailDH);

			listsp.add(c.getSanPham());
			quanity.put(c.getSanPham().getId(), Integer.toString(c.getSoLuong()));
		}

		chiTietDonHangService.save(listDetailDH);

		cleanUpAfterCheckOut(req);
		model.addAttribute("donhang", donhang);
		model.addAttribute("cart", listsp);
		model.addAttribute("quanity", quanity);
	}

	public void cleanUpAfterCheckOut(HttpServletRequest request) {
		NguoiDung currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		GioHang g = gioHangService.getGioHangByNguoiDung(currentUser);
		List<ChiMucGioHang> c = chiMucGioHangService.getChiMucGioHangByGioHang(g);
		chiMucGioHangService.deleteAllChiMucGiohang(c);
	}
}
