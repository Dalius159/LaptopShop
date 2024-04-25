package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Roles;
import Website.LaptopShop.Repositories.UserRepository;
import Website.LaptopShop.Repositories.RoleRepository;
import Website.LaptopShop.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import Website.LaptopShop.DTO.AcountDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Users findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Users saveUserForMember(Users nd) {
        nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));
        Set<Roles> setRoles = new HashSet<>();
        setRoles.add(roleRepo.findByRoleName("MEMBER"));
        nd.setRole(setRoles);
        return userRepo.save(nd);
    }

    @Override
    public Users findById(long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public Users updateUser(Users nd) {
        return userRepo.save(nd);
    }

    @Override
    public void changePass(Users us, String newPass) {
		us.setPassword(bCryptPasswordEncoder.encode(newPass));
		userRepo.save(us);
	}

    @Override
    public Page<Users> getUserByRole(Set<Roles> roles, int page) {
        return userRepo.findByRoleIn(roles, PageRequest.of(page - 1, 6));
    }

    @Override
    public List<Users> getUserByRole(Set<Roles> role) {return userRepo.findByRoleIn(role);}

    @Override
    public Users saveUserForAdmin(AcountDTO dto) {
        Users nd = new Users();
        nd.setFullName(dto.getFullName());
        nd.setAddress(dto.getAddress());
        nd.setEmail(dto.getEmail());
        nd.setPhoneNumber(dto.getPhoneNumber());
        nd.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        Set<Roles> roles = new HashSet<>();
        roles.add(roleRepo.findByRoleName(dto.getRoleName()));
        nd.setRole(roles);

        return userRepo.save(nd);
    }

    @Override
    public void deleteById(long id) {
        userRepo.deleteById(id);
    }
}
