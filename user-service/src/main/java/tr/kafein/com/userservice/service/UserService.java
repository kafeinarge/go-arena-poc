package tr.kafein.com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.kafein.com.userservice.data.User;
import tr.kafein.com.userservice.repository.UserRepository;
import tr.kafein.com.userservice.util.PasswordEncoder;

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

    @PostConstruct
    public void createFirstUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");
        user.setName("name");
        user.setSurname("surname");
        if(getByUsername(user.getUsername()) == null) {
            userRepository.save(user);
        }
    }
}
