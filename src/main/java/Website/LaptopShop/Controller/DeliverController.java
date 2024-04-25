package Website.LaptopShop.Controller;

import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/deliver")
@SessionAttributes("loggedInUser")
public class DeliverController {
//    @Autowired
//    private UserService userService;
//
//    @ModelAttribute("loggedInUser")
//    public Users loggedInUser() {}
//
//
//    @GetMapping(value= {"", "/order"})
//    public String deliverPage() {}
//
//    @GetMapping("/profile")
//    public String profilePage() {}
//
//    @PostMapping("/profile/update")
//    public String updateNguoiDung() {}
//
//    public Users getSessionUser(HttpServletRequest request) {}
}
