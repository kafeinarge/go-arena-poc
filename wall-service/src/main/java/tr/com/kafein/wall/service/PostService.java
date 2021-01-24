package tr.com.kafein.wall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tr.com.kafein.wall.accessor.UserServiceAccessor;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.dto.UserDto;
import tr.com.kafein.wall.repository.PostRepository;
import tr.com.kafein.wall.type.ApprovalType;

import java.util.Date;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserServiceAccessor userServiceAccessor;

    public Page<Post> allPageable(Pageable pageable) {
        Page<Post> result = postRepository.findAll(pageable);
        if (result.getNumberOfElements() > 0) {
            final UserDto[] user = {null};
            result.get().forEach(post -> {
                if (user[0] == null) {
                    user[0] = userServiceAccessor.getById(post.getUserId());
                }
                post.setUser(user[0]);
            });
        }

        return result;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent())
            return post.get();
        else
            throw new NotFoundException("Post id :[" + id + "] could not found");
    }

    public Post update(Long id, Post entity) {
        Post real = getById(id);
        entity.setId(real.getId());
        entity.setCreationDate(real.getCreationDate());
        entity.setUserId(real.getUserId());
        entity.setApproval(real.getApproval());
        if (entity.getPreview() == null)
            entity.setPreview(real.getPreview());
        return postRepository.save(entity);
    }

    public Post upload(String preview, String text) {
        Post post = new Post();
        post.setPreview(preview);
        post.setText(text);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUserId(userServiceAccessor.findByUsername(username).getId());
        post.setCreationDate(new Date());
        post.setApproval(ApprovalType.WAITING);
        return postRepository.save(post);
    }

    public Post updateApprovalStatus(Long id, ApprovalType approvalType) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userServiceAccessor.findByUsername(username).isAdmin()) {
            throw new RuntimeException("Bu işlemi sadece adminler gerçekleştirebilir");
        }
        Post post = getById(id);
        post.setApproval(approvalType);
        return postRepository.save(post);
    }
}
