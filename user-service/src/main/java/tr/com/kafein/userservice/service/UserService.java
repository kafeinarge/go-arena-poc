package tr.com.kafein.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.repository.UserRepository;
import tr.com.kafein.userservice.util.PasswordEncoder;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getOne() {
        return userRepository.findAll().get(0);
    }

    @PostConstruct
    public void createFirstUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");
        user.setName("name");
        user.setSurname("surname");
        if(getByUsername(user.getUsername()) == null) {
            save(user);
        }
    }
}
