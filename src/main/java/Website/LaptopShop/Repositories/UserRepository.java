package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Page<Users> findByRoleIn(Set<Roles> role, Pageable of);
}
