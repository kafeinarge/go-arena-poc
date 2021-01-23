package tr.com.kafein.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.kafein.userservice.data.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
