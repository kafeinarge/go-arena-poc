package tr.com.kafein.userservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static tr.com.kafein.userservice.constants.Constants.USER_NOT_FOUND_MSG;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException(String.format(USER_NOT_FOUND_MSG, id));
        }
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
        user.setAdmin(false);
        if(getByUsername(user.getUsername()) == null) {
            save(user);
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setName("admin name");
        admin.setSurname("admin surname");
        admin.setAdmin(true);
        if(getByUsername(admin.getUsername()) == null) {
            save(admin);
        }
    }
}
