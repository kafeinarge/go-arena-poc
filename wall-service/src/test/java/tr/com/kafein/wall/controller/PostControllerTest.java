package tr.com.kafein.wall.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.dto.PostDto;
import tr.com.kafein.wall.mapper.PostMapper;
import tr.com.kafein.wall.service.PostService;
import tr.com.kafein.wall.type.ApprovalType;
import tr.com.kafein.wall.util.Base64Util;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;

class PostControllerTest {
    private PostService mockService;
    private PostMapper mockMapper;
    private PostController controller;

    @BeforeEach
    void setUp() {
        mockService = mock(PostService.class);
        mockMapper = mock(PostMapper.class);
        controller = new PostController(mockService, mockMapper);
    }

    @Test
    void allPageable() {
        final int pageNo = 0;
        final int pageSize = 10;
        final String sortBy = "id";
        final String direction = "ASC";
        final Post post1 = new Post();
        final Post post2 = new Post();
        final PostDto postDto1 = new PostDto();
        final PostDto postDto2 = new PostDto();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.valueOf(direction), sortBy);
        List<Post> mockPosts = Arrays.asList(post1, post2);
        when(mockService.allPageable(pageable)).thenReturn(new PageImpl<>(mockPosts));
        when(mockMapper.toDto(post1)).thenReturn(postDto1);
        when(mockMapper.toDto(post2)).thenReturn(postDto2);

        Page<PostDto> result = controller.allPageable(pageNo, pageSize, sortBy, direction);

        Assertions.assertEquals(mockPosts.size(), result.getTotalElements());
        Assertions.assertEquals(mockPosts.size(), result.getContent().size());
        Assertions.assertTrue(result.getContent().contains(postDto1));
        Assertions.assertTrue(result.getContent().contains(postDto2));
    }

    @Test
    void delete() {
        final long id = 1L;

        controller.delete(id);

        Mockito.verify(mockService, times(1)).delete(id);
    }

    @Test
    void update() {
        final long id = 1L;
        final PostDto postDto = new PostDto();
        postDto.setId(1L);
        final Post post = new Post();
        post.setId(2L);
        final Post updatedPost = new Post();
        updatedPost.setId(3L);
        final PostDto updatedDto = new PostDto();
        updatedDto.setId(4L);

        when(mockMapper.toEntity(postDto)).thenReturn(post);
        when(mockService.update(id, post)).thenReturn(updatedPost);
        when(mockMapper.toDto(updatedPost)).thenReturn(updatedDto);

        PostDto result = controller.update(id, postDto);

        assertEquals(updatedDto, result);
    }

    @Test
    void uploadFile() {
        MultipartFile mockfile = mock(MultipartFile.class);
        final String text = MOCK_STRING;
        final Post post = new Post();
        final PostDto postDto = new PostDto();
        MockedStatic<Base64Util> base64UtilMockedStatic = mockStatic(Base64Util.class);

        base64UtilMockedStatic.when(() -> Base64Util.toBase64(mockfile)).thenReturn(MOCK_STRING);
        when(mockService.upload(MOCK_STRING, text)).thenReturn(post);
        when(mockMapper.toDto(post)).thenReturn(postDto);

        PostDto result = controller.uploadFile(mockfile, text);

        assertEquals(postDto, result);

        base64UtilMockedStatic.close();
    }

    @Test
    void uploadApprovalType() {
        final long id = 1L;
        final ApprovalType approvalType = ApprovalType.APPROVED;
        final Post post = new Post();
        final PostDto postDto = new PostDto();

        when(mockService.updateApprovalStatus(id, approvalType)).thenReturn(post);
        when(mockMapper.toDto(post)).thenReturn(postDto);

        PostDto result = controller.uploadApprovalType(id, approvalType);

        assertEquals(postDto, result);
    }
}
