package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Roles;
import Website.LaptopShop.Repositories.RoleRepository;
import Website.LaptopShop.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository Rep;

    @Override
    public Roles findByRoleName(String roleName) {
        return Rep.findByRoleName(roleName);
    }
}
