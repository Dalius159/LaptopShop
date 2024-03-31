package Website.LaptopShop.Controller;


import Website.LaptopShop.DTO.PasswordDTO;
import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.Entities.Orders;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Services.OrderService;
import Website.LaptopShop.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")
public class ClientAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("loggedInUser")
    public Users loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    public Users getSessionUser(HttpServletRequest request) {
        return (Users) request.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/account")
    public String accountPage(HttpServletRequest res, Model model) {
        Users currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        List<Orders> list = orderService.getOrderByUser(currentUser);
        Collections.reverse(list);
        model.addAttribute("list",list);
        return "client/account";
    }

    // TODO: obscure, need testing
    @GetMapping("/changeInformation")
    public String clientChangeInformationPage(HttpServletRequest res,Model model) {
        Users currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        return "client/information";
    }

    // TODO: obscure, need testing
    @GetMapping("/changePassword")
    public String clientChangePasswordPage() {
        return "client/passwordChange";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseObject commitChange(HttpServletRequest res, @RequestBody Users ng) {
        Users currentUser = getSessionUser(res);
        currentUser.setFullName(ng.getFullName());
        currentUser.setPhoneNumber(ng.getPhoneNumber());
        currentUser.setAddress(ng.getAddress());
        userService.updateUser(currentUser);
        return new ResponseObject();
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseObject passwordChange(HttpServletRequest res,@RequestBody PasswordDTO dto) {
        Users currentUser = getSessionUser(res);
        if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
            ResponseObject re = new ResponseObject();
            re.setStatus("old");
            return re;
        }
        userService.changePass(currentUser, dto.getNewPassword());
        return new ResponseObject();
    }
}
