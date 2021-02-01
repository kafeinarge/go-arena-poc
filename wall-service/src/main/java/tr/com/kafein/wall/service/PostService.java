package tr.com.kafein.wall.service;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserServiceAccessor userServiceAccessor;

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
            final UserDto[] user = {null};
            page.get().forEach(post -> {
                if (user[0] == null) {
                    user[0] = userServiceAccessor.getById(post.getUserId());
                }
                post.setUser(user[0]);
            });
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent())
            return post.get();
        else
            throw new NotFoundException("Post id :[" + id + "] bulunamadı");
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
        //TODO @mahmut, admin paneli çıkınca WAITING olacak
        post.setApproval(ApprovalType.APPROVED);
        return postRepository.save(post);
    }

    public Post updateApprovalStatus(Long id, ApprovalType approvalType) {
        if (!isAdminSession()) {
            throw new InternalServerError("Bu işlemi sadece adminler gerçekleştirebilir");
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
