package tr.com.kafein.userservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.service.UserService;

import static tr.com.kafein.userservice.TestConstants.MOCK_STRING;

class UserControllerTest {
    private UserService mockService;
    private UserController controller;
    private final String username = MOCK_STRING;

    @BeforeEach
    void setUp() {
        mockService = Mockito.mock(UserService.class);
        controller = new UserController(mockService);
    }

    @Test
    void findByUsername() {
        User user = new User();
        Mockito.when(mockService.getByUsername(username)).thenReturn(user);

        User result = controller.findByUsername(username);
        Assertions.assertEquals(user, result);
    }

    @Test
    void getOne() {
        User user = new User();
        Mockito.when(mockService.getOne()).thenReturn(user);

        User result = controller.getOne();
        Assertions.assertEquals(user, result);
    }

    @Test
    void getById() {
        User user = new User();
        user.setId(1L);
        Mockito.when(mockService.getById(user.getId())).thenReturn(user);

        User result = controller.getById(user.getId());
        Assertions.assertEquals(user, result);
    }
}
