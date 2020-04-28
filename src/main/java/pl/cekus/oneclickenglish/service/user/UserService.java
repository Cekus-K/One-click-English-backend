package pl.cekus.oneclickenglish.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.repository.UserRepository;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password, String passwordConfirm) {
        if (userRepository.findUserByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordConfirm(passwordEncoder.encode(passwordConfirm));
            user.setRole(Roles.USER.toString());
            userRepository.save(user);
            logger.info("created user - " + user.getUsername());
            return user;
        }
        return userRepository.findUserByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getCurrentLoggedInUser() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.info("get current logged-in user");
        } catch (ClassCastException e) {
            userDetails = userRepository.findUserByUsername("admin");
            logger.info("get default admin user");
        }
        return userRepository.findUserByUsername(userDetails.getUsername());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addAdminToDb() {
        User admin = createUser("admin", "password", "password");
        admin.setRole(Roles.ADMIN.toString());
        userRepository.save(admin);
    }
}
