package tr.com.kafein.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/get-one")
    public User getOne() {
        return userService.getOne();
    }

    @GetMapping("/{id}")
    User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
}
