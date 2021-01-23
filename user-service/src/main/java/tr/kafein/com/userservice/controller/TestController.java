package tr.kafein.com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.kafein.com.userservice.data.User;
import tr.kafein.com.userservice.repository.UserRepository;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String test(@RequestParam("data") String data) {
        return "hello" + data;
    }

    @GetMapping("/get-one")
    public User getOne() {
        if(userRepository.findAll().size() == 0) {
            return userRepository.save(new User());
        } else {
            return userRepository.findAll().get(0);
        }
    }
}
