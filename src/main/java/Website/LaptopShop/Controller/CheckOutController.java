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
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartPointerService cartPointerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailsService orderDetailsService;

	@ModelAttribute("loggedInUser")
	public Users loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}

	public Users getSessionUser(HttpServletRequest request) {
		return (Users) request.getSession().getAttribute("loggedInUser");
	}

	@GetMapping("/checkout")
	public String checkoutPage(HttpServletRequest req, Model model) {
		Users currentUser = getSessionUser(req);
		Map<Long, Long> quantity = new HashMap<>();
		List<Product> productList = new ArrayList<>();

		Cart g = cartService.getCartByUser(currentUser);

		List<CartPointer> cartPointerList = cartPointerService.getCartPointerByCart(g);

		for (CartPointer c : cartPointerList) {
			productList.add(c.getProduct());
			quantity.put(c.getProduct().getId(), (long) c.getQuantity());
		}

//      TODO: fix typo
		model.addAttribute("cart", productList);
		model.addAttribute("quantity", quantity);
		model.addAttribute("user", currentUser);
		model.addAttribute("order", new Orders());

		return "client/checkout";
	}

	@PostMapping("/complete-order")
	public String completeOrder(@ModelAttribute("order") Orders order, HttpServletRequest req, HttpServletResponse response, Model model) {
		SaveOrder(order, req, response, model, (byte) 0);

		return "redirect:/thankyou";
	}

	@GetMapping(value = "/thankyou")
	public String thankyouPage(HttpServletRequest req, Model model) {
		Users currentUser = getSessionUser(req);
		Orders order = orderService.findLatestOrderByOrdererID(currentUser.getId());
		Map<Long, Long> quantity = new HashMap<>();
		List<Product> productList = new ArrayList<>();

		List<OrderDetails> orderDetails = order.getOrderDetailsList();
		for (OrderDetails c : orderDetails) {
			productList.add(c.getProduct());
			quantity.put(c.getProduct().getId(), (long) c.getOrderQuantity());
		}

		model.addAttribute("order", order);
		model.addAttribute("cart", productList);
		model.addAttribute("quantity", quantity);

		return "client/thank-you";
	}

	public void SaveOrder(Orders order, HttpServletRequest req, HttpServletResponse response, Model model, byte status) {
		if (status == 1) {order.setNote("Paid");} else {order.setNote("Cash-on-delivery payment");}
		order.setOrderDate(new Date());
		order.setOrderStatus("Waiting for Delivery");

		Users currentUser = getSessionUser(req);
		Map<Long, Long> quanity = new HashMap<>();
		List<Product> productList = new ArrayList<>();
		List<OrderDetails> listDetail = new ArrayList<>();

		order.setOrderer(currentUser);
		System.out.println(order.getId());
		Orders d = orderService.save(order);
		Cart g = cartService.getCartByUser(currentUser);
		List<CartPointer> cartPointerList = cartPointerService.getCartPointerByCart(g);
		for (CartPointer c : cartPointerList) {
			OrderDetails detailDH = new OrderDetails();
			detailDH.setProduct(c.getProduct());
			detailDH.setCost(c.getQuantity() * c.getProduct().getPrice());
			detailDH.setOrderQuantity(c.getQuantity());
			detailDH.setOrder(d);
			listDetail.add(detailDH);

			productList.add(c.getProduct());
			quanity.put(c.getProduct().getId(), (long) c.getQuantity());
		}

		orderDetailsService.save(listDetail);

		cleanUpAfterCheckOut(req);
		model.addAttribute("order", order);
		model.addAttribute("cart", productList);
		model.addAttribute("quanity", quanity);
	}

	public void cleanUpAfterCheckOut(HttpServletRequest request) {
		Users currentUser = getSessionUser(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Cart g = cartService.getCartByUser(currentUser);
		List<CartPointer> c = cartPointerService.getCartPointerByCart(g);
		cartPointerService.deleteAllCartPointer(c);
	}
}
