package Website.LaptopShop.Services;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String email, String password);
}
