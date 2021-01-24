package tr.com.kafein.wall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.kafein.wall.data.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
