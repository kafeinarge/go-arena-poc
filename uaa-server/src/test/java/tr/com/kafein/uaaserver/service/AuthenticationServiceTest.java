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
import tr.com.kafein.uaaserver.dto.AccessTokenDto;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;

import java.util.Collections;
import java.util.Date;

import static tr.com.kafein.uaaserver.TestConstants.MOCK_STRING;
import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

class AuthenticationServiceTest {
    private CustomAuthenticationManager mockAuthManager;
    private AuthenticationService service;
    private final String username = MOCK_STRING;
    private final String password = MOCK_STRING;

    @BeforeEach
    void setUp() {
        mockAuthManager = Mockito.mock(CustomAuthenticationManager.class);
        service = new AuthenticationService(mockAuthManager);
    }

    @Test
    void getToken_WhenInvalidCredentialsIsGiven_ThenThrowException() {
        Mockito.when(mockAuthManager.authenticate(username, password)).thenReturn(new UsernamePasswordAuthenticationToken(null, password, Collections.emptyList()));

        UnauthorizedException expectedException = Assertions.assertThrows(UnauthorizedException.class, () -> service.getToken(username, password));
        Assertions.assertEquals(UNAUTHORIZED_MSG, expectedException.getMessage());
    }

    @Test
    void getToken_WhenValidCredentialsIsGiven_ThenThrowException() {
        UsernamePasswordAuthenticationToken tokenDto = new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        Mockito.when(mockAuthManager.authenticate(username, password)).thenReturn(tokenDto);

        MockedStatic<Jwts> jwtsMockedStatic = Mockito.mockStatic(Jwts.class);
        JwtBuilder mockJwtBuilder = Mockito.mock(JwtBuilder.class);
        Mockito.when(mockJwtBuilder.setSubject(username)).thenReturn(mockJwtBuilder);
        Mockito.when(mockJwtBuilder.setExpiration(Mockito.any(Date.class))).thenReturn(mockJwtBuilder);
        Mockito.when(mockJwtBuilder.signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)).thenReturn(mockJwtBuilder);
        String token = MOCK_STRING;
        Mockito.when(mockJwtBuilder.compact()).thenReturn(token);
        jwtsMockedStatic.when(Jwts::builder).thenReturn(mockJwtBuilder);

        AccessTokenDto result = service.getToken(username, password);

        Assertions.assertEquals(token, result.getToken());

        jwtsMockedStatic.close();
    }
}
