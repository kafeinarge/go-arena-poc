package tr.com.kafein.uaaserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.SecurityUser;
import tr.com.kafein.uaaserver.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static tr.com.kafein.uaaserver.TestConstants.MOCK_STRING;
import static tr.com.kafein.uaaserver.util.Constants.INVALID_USERNAME_MSG;

class UserDetailsServiceTest {
    private UserServiceAccessor mockAccessor;
    private UserDetailsService userDetailsService;
    private final String username = MOCK_STRING;

    @BeforeEach
    void setUp() {
        mockAccessor = Mockito.mock(UserServiceAccessor.class);
        userDetailsService = new UserDetailsService(mockAccessor);
    }

    @Test
    void loadUserByUsername_WhenInvalidLoginNameIsGiven_ThenThrowException() {
        Mockito.when(mockAccessor.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
        Assertions.assertEquals(String.format(INVALID_USERNAME_MSG, username), result.getMessage());

        String trimmableUsername = MOCK_STRING + "   ";
        UsernameNotFoundException result2 = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(trimmableUsername));
        Assertions.assertEquals(String.format(INVALID_USERNAME_MSG, username), result2.getMessage());
    }

    @Test
    void loadUserByUsername_WhenValidLoginNameIsGiven_ThenThrowException() {
        final UserDto userDto = new UserDto(1L, username, MOCK_STRING);
        Mockito.when(mockAccessor.findByUsername(username)).thenReturn(userDto);

        SecurityUser userDetails = (SecurityUser)userDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(MOCK_STRING, userDetails.getPassword());
        assertEquals(username, userDetails.getName());
        assertEquals(1L, userDetails.getId());
    }
}
