package tr.com.kafein.wall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.type.ApprovalType;

import javax.transaction.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByApproval(ApprovalType approvalType, Pageable pageable);
}
