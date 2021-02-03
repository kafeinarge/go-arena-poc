package tr.com.kafein.wall.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.kafein.wall.accessor.UserServiceAccessor;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.dto.UserDto;
import tr.com.kafein.wall.exception.InternalServerError;
import tr.com.kafein.wall.exception.NotFoundException;
import tr.com.kafein.wall.repository.PostRepository;
import tr.com.kafein.wall.type.ApprovalType;

import java.util.Date;
import java.util.Optional;

import static tr.com.kafein.wall.util.Constants.ADMIN_PERMISSION_MSG;
import static tr.com.kafein.wall.util.Constants.POST_NOT_FOUND_MSG;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserServiceAccessor userServiceAccessor;

    public PostService(PostRepository postRepository, UserServiceAccessor userServiceAccessor) {
        this.postRepository = postRepository;
        this.userServiceAccessor = userServiceAccessor;
    }

    public Page<Post> allPageable(Pageable pageable) {
        Page<Post> result = getAllPageable(pageable);
        fillUserFieldsToPostPage(result);
        return result;
    }

    private Page<Post> getAllPageable(Pageable pageable) {
        if (isAdminSession()) {
            return postRepository.findAllByApproval(ApprovalType.WAITING, pageable);
        } else {
            return postRepository.findAllByApproval(ApprovalType.APPROVED, pageable);
        }
    }

    private void fillUserFieldsToPostPage(Page<Post> page) {
        if (page.getNumberOfElements() > 0) {
            page.get().forEach(post -> {
                UserDto user = userServiceAccessor.getById(post.getUserId());
                post.setUser(user);
            });
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new NotFoundException(String.format(POST_NOT_FOUND_MSG, id));
        }
    }

    public Post update(Long id, Post entity) {
        Post real = getById(id);
        entity.setId(real.getId());
        entity.setCreationDate(real.getCreationDate());
        entity.setUserId(real.getUserId());
        entity.setApproval(real.getApproval());
        if (entity.getPreview() != null) {
            entity.setPreview(real.getPreview());
        }
        return postRepository.save(entity);
    }

    public Post upload(String preview, String text) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userServiceAccessor.findByUsername(username).getId();
        Post post = Post.builder()
                .preview(preview)
                .text(text)
                .userId(userId)
                .creationDate(new Date())
                .approval(ApprovalType.WAITING)
                .build();
        return postRepository.save(post);
    }

    public Post updateApprovalStatus(Long id, ApprovalType approvalType) {
        if (!isAdminSession()) {
            throw new InternalServerError(ADMIN_PERMISSION_MSG);
        }
        Post post = getById(id);
        post.setApproval(approvalType);
        return postRepository.save(post);
    }

    private boolean isAdminSession() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userServiceAccessor.findByUsername(username).isAdmin();
    }
}
