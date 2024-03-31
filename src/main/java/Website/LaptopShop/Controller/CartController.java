package Website.LaptopShop.Controller;

import Website.LaptopShop.Entities.CartPointer;
import Website.LaptopShop.Entities.Cart;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Product;
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
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
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

	@GetMapping("/cart")
	public String cartPage(HttpServletRequest req, HttpServletResponse res, Model model) {
		Users currentUser = getSessionUser(req);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<Long, Long> quantity = new HashMap<>();
		List<Product> productList = new ArrayList<>();
		Cookie[] cl = req.getCookies();
		//Get from cookie
		if (auth == null || auth.getPrincipal() == "anonymousUser") {

			Set<Long> idList = new HashSet<>();
			for (Cookie cookie : cl) {
				if (cookie.getName().matches("[0-9]+")) {
					idList.add(Long.parseLong(cookie.getName()));
					quantity.put(Long.parseLong(cookie.getName()), Long.parseLong(cookie.getValue()));
				}

			}
			productList = productService.getAllProductByList(idList);
			//get from database
		} else {
			Cart g = cartService.getCartByUser(currentUser);
			if (g == null) {
				g = new Cart();
				g.setUser(currentUser);
				cartService.save(g);
			}
			List<CartPointer> cartPointerList = cartPointerService.getCartPointerByCart(g);
			CartPointer cartPointer;
			int flag;
			var quantity2 = 1;
			for (Cookie cookie : cl) {
				flag = 0;
				if (cookie.getName().matches("[0-9]+")) {
					long id = Long.parseLong(cookie.getName());
					quantity2 = Integer.parseInt(cookie.getValue());
					for (CartPointer point : cartPointerList) {
						cartPointer = point;
						if (id == cartPointer.getProduct().getId()) {
							cartPointer.setQuantity(cartPointer.getQuantity() + quantity2);
							flag = 1;
							break;
						}

						cartPointerService.saveCartPointer(cartPointer);
					}

					if (flag == 0) {
						cartPointer = new CartPointer();
						cartPointer.setCart(g);
						cartPointer.setProduct(productService.getProductById(id));
						cartPointer.setQuantity(quantity2);
						cartPointerList.add(cartPointer);

						cartPointerService.saveCartPointer(cartPointer);
					}
				}
			}

			for (CartPointer c : cartPointerList) {
				productList.add(c.getProduct());
				quantity.put(c.getProduct().getId(), (long) c.getQuantity());
			}

			ClearUpRightBeforeCheckout(req, res);
		}

		model.addAttribute("checkEmpty", productList.size());
		model.addAttribute("cart", productList);
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
