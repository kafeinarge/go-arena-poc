package tr.com.kafein.userservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.webjars.NotFoundException;
import tr.com.kafein.userservice.data.User;
import tr.com.kafein.userservice.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static tr.com.kafein.userservice.TestConstants.MOCK_STRING;
import static tr.com.kafein.userservice.constants.Constants.USER_NOT_FOUND_MSG;

class UserServiceTest {
    private UserRepository mockRepository;
    private BCryptPasswordEncoder mockEncoder;
    private UserService service;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(UserRepository.class);
        mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        service = new UserService(mockRepository, mockEncoder);
    }

    @Test
    void save() {
        String encodedPassword = "encoded";
        User paramUser = new User();
        paramUser.setPassword(MOCK_STRING);

        Mockito.when(mockEncoder.encode(paramUser.getPassword())).thenReturn(encodedPassword);

        User expectedUser = new User();
        expectedUser.setPassword(encodedPassword);
        Mockito.when(mockRepository.save(expectedUser)).thenReturn(expectedUser);

        User result = service.save(paramUser);

        Assertions.assertEquals(expectedUser, result);
    }

    @Test
    void getById_WhenInvalidIdIsGiven_ThenThrowException() {
        Long id = 1L;

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException result = Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
        String expectedMsg = String.format(USER_NOT_FOUND_MSG, id);

        Assertions.assertEquals(expectedMsg, result.getMessage());
    }

    @Test
    void getById_WhenValidIdIsGiven_ThenThrowException() {
        Long id = 1L;
        User user = new User();

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(user));

        User result = service.getById(id);

        Assertions.assertEquals(user, result);
    }

    @Test
    void getByUsername() {
        String username = MOCK_STRING;
        User user = new User();

        Mockito.when(mockRepository.findByUsername(username)).thenReturn(user);

        User result = service.getByUsername(username);

        Assertions.assertEquals(user, result);
    }

    @Test
    void getOne() {
        User user = new User();
        List<User> allUser = Collections.singletonList(user);
        Mockito.when(mockRepository.findAll()).thenReturn(allUser);

        User result = service.getOne();

        Assertions.assertEquals(user, result);
    }
}
