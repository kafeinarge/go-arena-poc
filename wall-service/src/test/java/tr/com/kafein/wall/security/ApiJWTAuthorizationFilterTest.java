package tr.com.kafein.wall.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;
import static tr.com.kafein.wall.TestConstants.ROLE1;
import static tr.com.kafein.wall.TestConstants.ROLE2;
import static tr.com.kafein.wall.util.Constants.HEADER_STRING;
import static tr.com.kafein.wall.util.Constants.TOKEN_AUTHORITIES_KEY;
import static tr.com.kafein.wall.util.Constants.TOKEN_PREFIX;
import static tr.com.kafein.wall.util.Constants.TOKEN_SECRET;

class ApiJWTAuthorizationFilterTest {
    private HttpServletRequest mockRequest;
    private ApiJWTAuthorizationFilter filter;

    @BeforeEach
    void setUp() {
        AuthenticationManager mockAuthManager = Mockito.mock(AuthenticationManager.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        filter = new ApiJWTAuthorizationFilter(mockAuthManager);
    }

    @Test
    void getAuthentication_WhenRequestWithInvalidHeaderIsGiven_ThenReturnNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getAuthentication = ApiJWTAuthorizationFilter.class.getDeclaredMethod("getAuthentication", HttpServletRequest.class);
        getAuthentication.setAccessible(true);

        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn(null);
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));

        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn("");
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));

        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn(" ");
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));

        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn(MOCK_STRING);
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));
    }

    @Test
    void getAuthentication_WhenRequestWithInvalidUserIsGiven_ThenReturnNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getAuthentication = ApiJWTAuthorizationFilter.class.getDeclaredMethod("getAuthentication", HttpServletRequest.class);
        getAuthentication.setAccessible(true);

        String token = TOKEN_PREFIX + MOCK_STRING;
        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn(token);

        MockedStatic<Jwts> jwtsMockedStatic = mockStatic(Jwts.class);
        JwtParser mockJwtParser = mock(JwtParser.class);
        when(mockJwtParser.setSigningKey(TOKEN_SECRET)).thenReturn(mockJwtParser);

        Jws mockJws = mock(Jws.class);
        Claims mockClaims = mock(Claims.class);
        when(mockClaims.getSubject()).thenReturn(null);
        when(mockJws.getBody()).thenReturn(mockClaims);
        when(mockJwtParser.parseClaimsJws(MOCK_STRING)).thenReturn(mockJws);
        jwtsMockedStatic.when(Jwts::parser).thenReturn(mockJwtParser);

        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));

        when(mockClaims.getSubject()).thenReturn("");
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));

        when(mockClaims.getSubject()).thenReturn(" ");
        Assertions.assertNull(getAuthentication.invoke(filter, mockRequest));
        jwtsMockedStatic.close();
    }

    @Test
    void getAuthentication_WhenRequestWithValidUserIsGiven_ThenReturnNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getAuthentication = ApiJWTAuthorizationFilter.class.getDeclaredMethod("getAuthentication", HttpServletRequest.class);
        getAuthentication.setAccessible(true);

        String token = TOKEN_PREFIX + MOCK_STRING;
        Mockito.when(mockRequest.getHeader(HEADER_STRING)).thenReturn(token);

        MockedStatic<Jwts> jwtsMockedStatic = mockStatic(Jwts.class);
        JwtParser mockJwtParser = mock(JwtParser.class);
        when(mockJwtParser.setSigningKey(TOKEN_SECRET)).thenReturn(mockJwtParser);

        Jws mockJws = mock(Jws.class);
        Claims mockClaims = mock(Claims.class);
        final String user = MOCK_STRING;
        when(mockClaims.getSubject()).thenReturn(user);
        when(mockClaims.get(TOKEN_AUTHORITIES_KEY)).thenReturn(Collections.emptyList());
        when(mockJws.getBody()).thenReturn(mockClaims);
        when(mockJwtParser.parseClaimsJws(MOCK_STRING)).thenReturn(mockJws);
        jwtsMockedStatic.when(Jwts::parser).thenReturn(mockJwtParser);

        UsernamePasswordAuthenticationToken emptyAuthorityResult = (UsernamePasswordAuthenticationToken)getAuthentication.invoke(filter, mockRequest);
        Assertions.assertEquals(user, emptyAuthorityResult.getPrincipal());
        Assertions.assertNull(emptyAuthorityResult.getCredentials());
        Assertions.assertTrue(emptyAuthorityResult.getAuthorities().isEmpty());

        List<String> roles = Arrays.asList(ROLE1,ROLE2);
        when(mockClaims.get(TOKEN_AUTHORITIES_KEY)).thenReturn(roles);

        UsernamePasswordAuthenticationToken notEmptyAuthorityResult = (UsernamePasswordAuthenticationToken)getAuthentication.invoke(filter, mockRequest);

        Assertions.assertEquals(user, notEmptyAuthorityResult.getPrincipal());
        Assertions.assertNull(notEmptyAuthorityResult.getCredentials());

        List<GrantedAuthority> expectedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Assertions.assertEquals(expectedAuthorities,notEmptyAuthorityResult.getAuthorities());
        jwtsMockedStatic.close();
    }
}
