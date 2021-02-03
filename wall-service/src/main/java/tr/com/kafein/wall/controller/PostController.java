package tr.com.kafein.wall.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tr.com.kafein.wall.dto.PostDto;
import tr.com.kafein.wall.mapper.PostMapper;
import tr.com.kafein.wall.service.PostService;
import tr.com.kafein.wall.type.ApprovalType;

import static tr.com.kafein.wall.util.Base64Util.toBase64;

@RestController
public class PostController {

    private final PostService postService;
    private final PostMapper mapper;

    public PostController(PostService postService, PostMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public Page<PostDto> allPageable(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.valueOf(direction), sortBy);
        return postService.allPageable(pageable).map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        postService.delete(id);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        return mapper.toDto(postService.update(id, mapper.toEntity(postDto)));
    }

    @PutMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostDto uploadFile(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart(value = "text", required = false) String text) {
        return mapper.toDto(postService.upload(toBase64(file), text));
    }

    @PutMapping("/{id}/approval/{approvalType}")
    public PostDto uploadApprovalType(@PathVariable("id") Long id, @PathVariable("approvalType") ApprovalType approvalType) {
        return mapper.toDto(postService.updateApprovalStatus(id, approvalType));
    }
}
