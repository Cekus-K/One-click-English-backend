package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.service.user.UserService;

@RestController
class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void registerUser(@RequestBody User user) {
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            userService.addUser(user.getUsername(), user.getPassword(), user.getPasswordConfirm());
        }
    }
}
