package tr.com.kafein.uaaserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static tr.com.kafein.uaaserver.TestConstants.MOCK_STRING;
import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

class CustomAuthenticationManagerTest {
    private UserDetailsService mockUserDetailsService;
    private BCryptPasswordEncoder mockEncoder;
    private CustomAuthenticationManager manager;
    private final String username = MOCK_STRING;
    private final String password = MOCK_STRING;

    @BeforeEach
    void setUp() {
        mockUserDetailsService = Mockito.mock(UserDetailsService.class);
        mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        manager = new CustomAuthenticationManager(mockUserDetailsService, mockEncoder);
    }

    @Test
    void authenticate_WhenInvalidUsernameIsGiven_ThenThrowException() {
        Mockito.when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(null);

        UnauthorizedException result = assertThrows(UnauthorizedException.class, () -> manager.authenticate(username, password));

        Assertions.assertEquals(username, result.getMessage());
    }

    @Test
    void authenticate_WhenInvalidPasswordIsGiven_ThenThrowException() {
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);
        Mockito.when(mockUserDetails.getPassword()).thenReturn(MOCK_STRING);
        Mockito.when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        Mockito.when(mockEncoder.matches(password, MOCK_STRING)).thenReturn(false);

        UnauthorizedException result = assertThrows(UnauthorizedException.class, () -> manager.authenticate(username, password));

        Assertions.assertEquals(UNAUTHORIZED_MSG, result.getMessage());
    }

    @Test
    void authenticate_WhenValidUserIsGiven_ThenThrowException() {
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);
        Mockito.when(mockUserDetails.getPassword()).thenReturn(MOCK_STRING);
        Mockito.when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        Mockito.when(mockEncoder.matches(password, MOCK_STRING)).thenReturn(true);

        Authentication result = manager.authenticate(username, password);

        Assertions.assertEquals(username, result.getPrincipal());
        Assertions.assertEquals(password, result.getCredentials());
        Assertions.assertTrue(result.getAuthorities().isEmpty());
    }
}
