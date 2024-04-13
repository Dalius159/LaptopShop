package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Roles;

import java.util.List;

public interface RoleService {
    Roles findByRoleName(String roleName);

    List<Roles> findAllRole();
}
