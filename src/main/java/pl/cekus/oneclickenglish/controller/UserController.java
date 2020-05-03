package pl.cekus.oneclickenglish.controller;
import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.service.user.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user);
    }
}
