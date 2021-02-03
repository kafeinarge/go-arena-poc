package tr.com.kafein.wall.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.com.kafein.wall.accessor.UserServiceAccessor;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.dto.UserDto;
import tr.com.kafein.wall.exception.InternalServerError;
import tr.com.kafein.wall.exception.NotFoundException;
import tr.com.kafein.wall.repository.PostRepository;
import tr.com.kafein.wall.type.ApprovalType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;
import static tr.com.kafein.wall.util.Constants.ADMIN_PERMISSION_MSG;
import static tr.com.kafein.wall.util.Constants.POST_NOT_FOUND_MSG;

class PostServiceTest {
    private PostRepository mockRepository;
    private UserServiceAccessor mockUserAccessor;
    private PostService service;

    @BeforeEach
    void setUp() {
        mockRepository = mock(PostRepository.class);
        mockUserAccessor = mock(UserServiceAccessor.class);
        service = new PostService(mockRepository, mockUserAccessor);
    }

    @Test
    void allPageable_WhenAdminUserIsGiven_ThenReturnEqualsResult() {
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);

        getAdminUser(securityContextHolderMockedStatic);

        Pageable mockPageable = mock(Pageable.class);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setUserId(1L);
        Post post2 = new Post();
        post2.setId(2L);
        post2.setUserId(2L);

        UserDto user1 = new UserDto();
        user1.setId(1L);
        UserDto user2 = new UserDto();
        user2.setId(2L);

        List<Post> posts = Arrays.asList(post1, post2);

        Page page = new PageImpl(posts);

        when(mockRepository.findAll(mockPageable)).thenReturn(page);
        when(mockUserAccessor.getById(post1.getUserId())).thenReturn(user1);
        when(mockUserAccessor.getById(post2.getUserId())).thenReturn(user2);

        Page<Post> adminResult = service.allPageable(mockPageable);

        post1.setUser(user1);
        post2.setUser(user2);

        assertEquals(posts.size(), adminResult.getTotalElements());
        assertTrue(adminResult.getContent().contains(post1));
        assertTrue(adminResult.getContent().contains(post2));

        securityContextHolderMockedStatic.close();
    }

    @Test
    void getAllPageable_ForUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);

        getNormalUser(securityContextHolderMockedStatic);

        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(2L);
        List<Post> posts = Arrays.asList(post1, post2);
        Pageable mockPageable = mock(Pageable.class);

        when(mockRepository.findAllByApproval(ApprovalType.APPROVED, mockPageable)).thenReturn(new PageImpl<>(posts));

        Method getAllPageable = PostService.class.getDeclaredMethod("getAllPageable", Pageable.class);
        getAllPageable.setAccessible(true);
        Page<Post> result = (Page<Post>) getAllPageable.invoke(service, mockPageable);

        assertEquals(posts.size(), result.getTotalElements());
        assertEquals(posts.size(), result.getContent().size());
        assertTrue(result.getContent().contains(post1));
        assertTrue(result.getContent().contains(post2));

        securityContextHolderMockedStatic.close();
    }

    @Test
    void delete() {
        final long id = 1L;

        service.delete(id);

        verify(mockRepository, times(1)).deleteById(id);
    }

    @Test
    void getById_WhenInvalidIdIsGiven_ThenThrowException() {
        final long id = 1L;

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> service.getById(id));

        assertEquals(String.format(POST_NOT_FOUND_MSG, id), result.getMessage());
    }

    @Test
    void getById_WhenValidIdIsGiven_ThenThrowException() {
        final long id = 1L;
        final Post post = new Post();
        post.setId(1L);

        when(mockRepository.findById(id)).thenReturn(Optional.of(post));

        Post result = service.getById(id);

        assertEquals(post, result);
    }

    @Test
    void update() {
        final long id = 1L;
        final Date oldDate = new Date();
        final ApprovalType oldApprovalType = ApprovalType.WAITING;
        final Post oldData = new Post();
        oldData.setId(id);
        oldData.setCreationDate(oldDate);
        oldData.setUserId(id);
        oldData.setApproval(oldApprovalType);
        oldData.setPreview(MOCK_STRING);
        final Post newData = new Post();

        when(mockRepository.findById(id)).thenReturn(Optional.of(oldData));

        when(mockRepository.save(oldData)).thenReturn(oldData);

        Post result = service.update(id, newData);

        assertEquals(id, result.getId());
        assertEquals(oldDate, result.getCreationDate());
        assertEquals(id, result.getUserId());
        assertEquals(oldApprovalType, result.getApproval());
        assertEquals(MOCK_STRING, result.getPreview());
    }

    @Test
    void updateApprovalStatus_WhenUserIsGiven_ThenThrowExceptionWithMessage() {
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
        getNormalUser(securityContextHolderMockedStatic);

        final long id = 1L;
        final ApprovalType approvalType = ApprovalType.APPROVED;

        InternalServerError result = assertThrows(InternalServerError.class, () -> service.updateApprovalStatus(id, approvalType));

        assertEquals(ADMIN_PERMISSION_MSG, result.getMessage());
        securityContextHolderMockedStatic.close();
    }

    @Test
    void updateApprovalStatus_WhenAdminUserIsGiven_ThenThrowExceptionWithMessage() {
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
        getAdminUser(securityContextHolderMockedStatic);

        final long id = 1L;
        final ApprovalType approvalType = ApprovalType.APPROVED;
        final Post post = new Post();
        post.setId(id);

        when(mockRepository.findById(id)).thenReturn(Optional.of(post));
        when(mockRepository.save(post)).thenReturn(post);

        Post result = service.updateApprovalStatus(id, approvalType);

        assertEquals(approvalType, result.getApproval());
        securityContextHolderMockedStatic.close();
    }

    private void getAdminUser(MockedStatic<SecurityContextHolder> mockedStatic) {
        final String username = MOCK_STRING;

        SecurityContext mockContext = mock(SecurityContext.class);
        Authentication mockAuthentication = mock(Authentication.class);
        mockedStatic.when(SecurityContextHolder::getContext).thenReturn(mockContext);
        when(mockContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(username);

        UserDto user = new UserDto();
        user.setAdmin(true);
        when(mockUserAccessor.findByUsername(username)).thenReturn(user);
    }

    private void getNormalUser(MockedStatic<SecurityContextHolder> mockedStatic) {
        final String username = MOCK_STRING;

        SecurityContext mockContext = mock(SecurityContext.class);
        Authentication mockAuthentication = mock(Authentication.class);
        mockedStatic.when(SecurityContextHolder::getContext).thenReturn(mockContext);
        when(mockContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(username);

        UserDto user = new UserDto();
        user.setAdmin(false);
        when(mockUserAccessor.findByUsername(username)).thenReturn(user);

    }
}
