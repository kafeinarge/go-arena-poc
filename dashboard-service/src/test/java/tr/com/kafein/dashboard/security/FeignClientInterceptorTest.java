package tr.com.kafein.dashboard.security;

import feign.RequestTemplate;
import feign.template.HeaderTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static tr.com.kafein.dashboard.TestConstants.MOCK_STRING;
import static tr.com.kafein.dashboard.security.FeignClientInterceptor.AUTHORIZATION_HEADER;

class FeignClientInterceptorTest {

    private Authentication mockAuthentication;
    private SecurityContext mockSecurityContext;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockAuthentication = mock(Authentication.class);
        mockSecurityContext = mock(SecurityContext.class);
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void apply_whenAuthenticatedUserIsGiven_ThenAddAuthHeaderInTemplate() throws NoSuchFieldException, IllegalAccessException {
        MockedStatic mockedSecurityContext = mockStatic(SecurityContextHolder.class);
        MockedStatic mockedRequestContext = mockStatic(RequestContextHolder.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);

        when(mockRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(MOCK_STRING);
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        mockedRequestContext.when(RequestContextHolder::getRequestAttributes).thenReturn(servletRequestAttributes);

        RequestTemplate template = new RequestTemplate();
        FeignClientInterceptor interceptor = new FeignClientInterceptor();
        interceptor.apply(template);

        Field headersField = RequestTemplate.class.getDeclaredField("headers");
        headersField.setAccessible(true);
        Map<String, HeaderTemplate> headersValue = (Map<String, HeaderTemplate>) headersField.get(template);

        assertTrue(headersValue.containsKey(AUTHORIZATION_HEADER));
        assertTrue(headersValue.get(AUTHORIZATION_HEADER).getValues().contains(MOCK_STRING));

        mockedSecurityContext.close();
        mockedRequestContext.close();
    }
}
