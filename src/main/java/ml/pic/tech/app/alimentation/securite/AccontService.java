package ml.pic.tech.app.alimentation.securite;

import ml.pic.tech.app.alimentation.domaine.Role;
import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.repository.RoleRepository;
import ml.pic.tech.app.alimentation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccontService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;


    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public List<Role> allRoles() {
        return roleRepository.findAll();
    }
}
