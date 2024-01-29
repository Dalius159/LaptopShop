package Website.LaptopShop.Controller;

import Website.LaptopShop.DTO.SearchSanPhamObject;
import Website.LaptopShop.Entities.SanPham;
import Website.LaptopShop.Services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ClientSearchController {
	@Autowired
	private SanPhamService sanPhamService;

	@GetMapping("search")
	public String searchSP(@RequestParam(defaultValue = "1") int page,
						   @RequestParam String name,
						   @RequestParam(defaultValue = "") String sort,
						   @RequestParam(defaultValue = "") String range,
						   @RequestParam(defaultValue = "") String brand,
//						   todo: fix typo
						   @RequestParam(defaultValue = "") String manufactor,
						   Model model) {
		SearchSanPhamObject obj = new SearchSanPhamObject();
		obj.setKeyword(name.split(" "));
		obj.setSort(sort);
		obj.setDonGia(range);
		obj.setBrand(brand);
		obj.setManufactor(manufactor);
		Page<SanPham> list = sanPhamService.getSanPhamByTenSanPham(obj, page, 12);
		int totalPage = list.getTotalPages();
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("name", name);
		model.addAttribute("sort", sort);
		model.addAttribute("range", range);
		model.addAttribute("brand", brand);
		model.addAttribute("manufactor", manufactor);
		List<Integer> pagelist = new ArrayList<Integer>();

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
		Set<String> danhmuc = new HashSet<String>();
		Set<String> hangsx = new HashSet<String>();
		Iterable<SanPham> dum = sanPhamService.getSanPhamByTenSanPhamWithoutPaginate(obj);
		for (SanPham sp : dum) {
			danhmuc.add(sp.getDanhMuc().getTenDanhMuc());
			hangsx.add(sp.getHangSanXuat().getTenHangSanXuat());
		}
		model.addAttribute("danhmuc", danhmuc);
		model.addAttribute("hangsx", hangsx);

		return "client/searchResult";
	}
}
