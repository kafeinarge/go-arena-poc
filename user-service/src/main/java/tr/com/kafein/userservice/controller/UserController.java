package tr.com.kafein.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/get-one")
    public User getOne() {
        return userService.getOne();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
}
