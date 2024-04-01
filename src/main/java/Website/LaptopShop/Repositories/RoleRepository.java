package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String roleName);
}
