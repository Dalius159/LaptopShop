package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.AcountDTO;
import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Roles;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface UserService {
    Users findByEmail(String email);

    Users saveUserForMember(Users user);

    Users findById(long id);

    Users updateUser(Users user);

    void changePass(Users user, String newPass);

    Page<Users> getUserByRole(Set<Roles> role, int page);

    Users saveUserForAdmin(AcountDTO dto);

    void deleteById(long id);
}
