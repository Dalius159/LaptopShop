package Website.LaptopShop.Services;

import Website.LaptopShop.Entities.Roles;

public interface RoleService {
    Roles findByRoleName(String roleName);
}
