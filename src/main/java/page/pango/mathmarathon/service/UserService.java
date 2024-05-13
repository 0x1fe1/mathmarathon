package page.pango.mathmarathon.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import page.pango.mathmarathon.entity.User;
import page.pango.mathmarathon.entity.UserDTO;
import page.pango.mathmarathon.entity.UserRole;
import page.pango.mathmarathon.repositories.UserRepository;
import page.pango.mathmarathon.repositories.UserRoleRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User getUserByName(String userName) {
        return userRepository.getUserByName(userName);
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    public boolean addUser(UserDTO newUser) {
        if (!userRepository.existsByName(newUser.getName())) {
            User user = new User(
                null,
                newUser.getName(),
                newUser.getEmail(),
                encoder.encode(newUser.getPassword()),
                0L
            );
            userRepository.save(user);

            User savedUser = userRepository.getUserByName(user.getName());
            UserRole userRole = new UserRole(
                UserRole.Role.USER,
                savedUser.getId()
            );
            userRoleRepository.save(userRole);

            return true;
        }
        return false;
    }
}
