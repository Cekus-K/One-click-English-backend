package pl.cekus.oneclickenglish.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.repository.UserRepository;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(String username, String password, String passwordConfirm) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordConfirm(passwordEncoder.encode(passwordConfirm));
        user.setRole(Roles.USER.toString());
        userRepository.save(user);
        logger.info("created user - " + user.getUsername());
        return user;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addAdminToDb() {
        User user = addUser("admin", "password", "password");
        user.setRole(Roles.ADMIN.toString());
    }
}
