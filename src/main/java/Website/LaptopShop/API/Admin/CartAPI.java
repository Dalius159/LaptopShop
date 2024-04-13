package Website.LaptopShop.API.Admin;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.CartPointer;
import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.CartPointerService;
import Website.LaptopShop.Services.CartService;
import Website.LaptopShop.Services.UserService;
import Website.LaptopShop.Services.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/cart")
@SessionAttributes("loggedInUser")
public class CartAPI {

	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CartPointerService cartPointerService;

	@ModelAttribute("loggedInUser")
	public Users loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}

	public Users getSessionUser(HttpServletRequest request) {
		return (Users) request.getSession().getAttribute("loggedInUser");
	}

	@GetMapping("/addSanPham")
	public ResponseObject addToCart(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		ResponseObject ro = new ResponseObject();
		Product sp = productService.getProductById(Long.parseLong(id));
		if (sp.getWarehouseUnit() == 0) {
			ro.setStatus("false");
			return ro;
		}
		Users currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Use cookie to save
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			boolean found = false;
			for (Cookie clientCookie : clientCookies) {
				//If product already in cart => quantity ++
				if (clientCookie.getName().equals(id)) {
					clientCookie.setValue(Integer.toString(Integer.parseInt(clientCookie.getValue()) + 1));
					clientCookie.setPath("/");
					clientCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(clientCookie);
					found = true;
					break;
				}
			}
			//if product not in cookie, add to cookie
			if (!found) {
				Cookie c = new Cookie(id, "1");
				c.setPath("/");
				c.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(c);
			}
			//use dtb to save
		} else {
			Cart g = cartService.getCartByUser(currentUser);
			if (g == null) {
				g = new Cart();
				g.setUser(currentUser);
				g = cartService.save(g);
			}

			CartPointer c = cartPointerService.getCartPointerByProductAndCart(sp, g);
			//if not found cart pointer, create one
			if (c == null) {
				System.out.println(g.getUser().getEmail());
				System.out.println(g.getId());
				c = new CartPointer();
				c.setCart(g);
				c.setProduct(sp);
				c.setQuantity(1);
				//if product already in dtb, quantity ++
			} else {
				c.setQuantity(c.getQuantity() + 1);
			}
			cartPointerService.saveCartPointer(c);
		}
		ro.setStatus("success");
		return ro;
	}

	//TODO: fix typo
	@GetMapping("/changProductQuanity")
	public ResponseObject changeQuanity(@RequestParam String id, @RequestParam String value, HttpServletRequest request, HttpServletResponse response) {
		Users currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ResponseObject ro = new ResponseObject();
		//Save by cookie
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			for (Cookie clientCookie : clientCookies) {
				if (clientCookie.getName().equals(id)) {
					clientCookie.setValue(value);
					clientCookie.setPath("/");
					clientCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(clientCookie);
					break;
				}
			}
			//Save by dtb
		} else {
			Cart g = cartService.getCartByUser(currentUser);
			Product sp = productService.getProductById(Long.parseLong(id));
			CartPointer c = cartPointerService.getCartPointerByProductAndCart(sp, g);
			c.setQuantity(Integer.parseInt(value));
			cartPointerService.saveCartPointer(c);
		}
		ro.setStatus("success");
		return ro;
	}

	@GetMapping("/deleteFromCart")
	public ResponseObject deleteProduct(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		Users currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ResponseObject ro = new ResponseObject();
		//Save by cookie
		if (auth == null || auth.getPrincipal() == "anonymousUser") {
			Cookie[] clientCookies = request.getCookies();
			for (Cookie clientCookie : clientCookies) {
				if (clientCookie.getName().equals(id)) {
					clientCookie.setMaxAge(0);
					clientCookie.setPath("/");
					System.out.println(clientCookie.getMaxAge());
					response.addCookie(clientCookie);
					break;
				}
			}
			//Save by dtb
		} else {
			Cart g = cartService.getCartByUser(currentUser);
			Product sp = productService.getProductById(Long.parseLong(id));
			CartPointer c = cartPointerService.getCartPointerByProductAndCart(sp, g);
			cartPointerService.deleteCartPointer(c);
		}

		ro.setStatus("success");
		return ro;
	}
}
