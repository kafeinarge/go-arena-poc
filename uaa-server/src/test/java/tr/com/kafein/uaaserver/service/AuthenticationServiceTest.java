package tr.com.kafein.uaaserver.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;
import tr.com.kafein.uaaserver.dto.UserDto;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static tr.com.kafein.uaaserver.TestConstants.MOCK_STRING;
import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

class AuthenticationServiceTest {
    private UserServiceAccessor mockUserAccessor;
    private BCryptPasswordEncoder mockEncoder;
    private AuthenticationService service;
    private final String username = MOCK_STRING;
    private final String password = MOCK_STRING;

    @BeforeEach
    void setUp() {
        mockUserAccessor = Mockito.mock(UserServiceAccessor.class);
        mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        service = new AuthenticationService(mockUserAccessor, mockEncoder);
    }

    @Test
    void getToken_WhenInvalidUsernameIsGiven_ThenThrowException() {
        when(mockUserAccessor.findByUsername(username)).thenReturn(null);

        UnauthorizedException expectedException = Assertions.assertThrows(UnauthorizedException.class, () -> service.getToken(username, password));
        assertEquals(username, expectedException.getMessage());
    }

    @Test
    void getToken_WhenInvalidPasswordIsGiven_ThenThrowException() {
        UserDto userDto = new UserDto();
        userDto.setName(username);
        userDto.setPassword(MOCK_STRING);

        when(mockUserAccessor.findByUsername(username)).thenReturn(userDto);
        when(mockEncoder.matches(password, userDto.getPassword())).thenReturn(false);

        UnauthorizedException expectedException = Assertions.assertThrows(UnauthorizedException.class, () -> service.getToken(username, password));
        assertEquals(UNAUTHORIZED_MSG, expectedException.getMessage());
    }

    @Test
    void getToken_WhenValidCredentialsIsGiven_ThenThrowException() {
        UserDto userDto = new UserDto();
        userDto.setName(username);
        userDto.setPassword(MOCK_STRING);

        when(mockUserAccessor.findByUsername(username)).thenReturn(userDto);
        when(mockEncoder.matches(password, userDto.getPassword())).thenReturn(true);

        MockedStatic<Jwts> jwtsMockedStatic = Mockito.mockStatic(Jwts.class);
        JwtBuilder mockJwtBuilder = Mockito.mock(JwtBuilder.class);
        when(mockJwtBuilder.setSubject(username)).thenReturn(mockJwtBuilder);
        when(mockJwtBuilder.setExpiration(Mockito.any(Date.class))).thenReturn(mockJwtBuilder);
        when(mockJwtBuilder.signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)).thenReturn(mockJwtBuilder);
        String token = MOCK_STRING;
        when(mockJwtBuilder.compact()).thenReturn(token);
        jwtsMockedStatic.when(Jwts::builder).thenReturn(mockJwtBuilder);

        AccessTokenDto result = service.getToken(username, password);

        assertEquals(token, result.getToken());

        jwtsMockedStatic.close();
    }
}
