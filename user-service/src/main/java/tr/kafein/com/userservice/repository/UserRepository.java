package tr.kafein.com.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.kafein.com.userservice.data.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
