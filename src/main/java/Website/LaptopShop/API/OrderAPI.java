package Website.LaptopShop.API;

import Website.LaptopShop.DTO.SearchOrderObject;
import Website.LaptopShop.Entities.OrderDetails;
import Website.LaptopShop.Entities.Orders;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Services.OrderService;
import Website.LaptopShop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/don-hang")
public class OrderAPI {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	//Get orderList by search object
	@GetMapping("/all")
	public Page<Orders> getOrderByFilter(@RequestParam(defaultValue = "1") int page, @RequestParam String status,
										 @RequestParam String fromDate, @RequestParam String toDate) throws ParseException {

		SearchOrderObject object = new SearchOrderObject();
		object.setToDate(toDate);
		object.setOrderStatus(status);
		object.setFromDate(fromDate);
		return orderService.getAllOrderByFilter(object, page);
	}

	@GetMapping("/{id}")
	public Orders getOrderById(@PathVariable long id) {
		return orderService.findById(id);
	}

	// order assignment
	@PostMapping("/assign")
	public void orderAssignment(@RequestParam("deliverEmail") String deliverEmail,
								@RequestParam("orderID") long orderID) {
		Orders dh = orderService.findById(orderID);
		dh.setOrderStatus("Delivering");
		dh.setDeliver(userService.findByEmail(deliverEmail));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			String dateStr = format.format(new Date());
			Date date = format.parse(dateStr);
			dh.setDeliveryDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		orderService.save(dh);
	}

	// Order completed confirm
	@PostMapping("/update")
	public void orderComplete(@RequestParam("orderID") long orderID,
							  @RequestParam("adminNote") String adminNote) {
		Orders dh = orderService.findById(orderID);

		for (OrderDetails ct : dh.getOrderDetailsList()) {
			Product sp = ct.getProduct();
			sp.setSalesUnit(sp.getSalesUnit() + ct.getReceivedQuantity());
			sp.setWarehouseUnit(sp.getWarehouseUnit() - ct.getReceivedQuantity());
		}
		dh.setOrderStatus("Completed");
		String note = dh.getNote();
		if (!adminNote.equals("")) {
			note += "<br> Admin Note:\n" + adminNote;
		}
		dh.setNote(note);
		orderService.save(dh);
	}

	// Order canceled confirm
	@PostMapping("/cancel")
	public void orderCanceled(@RequestParam("orderID") long orderID) {
		Orders dh = orderService.findById(orderID);
		dh.setOrderStatus("Canceled");
		orderService.save(dh);
	}

	// Get data for statistical reports
	@GetMapping("/report")
	public List<Object> test() {
		return orderService.getOrderByMonthAndYear();
	}
}
