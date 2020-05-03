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

    public User createUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getUsername().equals("admin")) {
                user.setRole(Roles.ADMIN.toString());
            } else {
                user.setRole(Roles.USER.toString());
            }
            userRepository.save(user);
            logger.info("created user - " + user.getUsername());
            return user;
        }
        return userRepository.findUserByUsername(user.getUsername());
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
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setPasswordConfirm("password");
        admin.setRole(Roles.ADMIN.toString());
        createUser(admin);
    }
}
