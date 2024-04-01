package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.Users;
import Website.LaptopShop.Entities.Roles;
import Website.LaptopShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + username + " was not be found");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Roles> roles = user.getRole();
        for (Roles role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return new User(username, user.getPassword(), grantedAuthorities);
    }
}
