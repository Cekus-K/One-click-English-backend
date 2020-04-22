package pl.cekus.oneclickenglish.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.service.user.UserService;

@Controller
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(){
        return "register";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            userService.createUser(user.getUsername(), user.getPassword(), user.getPasswordConfirm());
        }
    }
}
