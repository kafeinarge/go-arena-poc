package tr.com.kafein.uaaserver.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;
import tr.com.kafein.uaaserver.dto.LoginRequestDto;
import tr.com.kafein.uaaserver.service.AuthenticationService;

import static tr.com.kafein.uaaserver.TestConstants.MOCK_STRING;

class TokenControllerTest {
    private AuthenticationService mockAuthService;
    private TokenController controller;

    @BeforeEach
    void setUp() {
        mockAuthService = Mockito.mock(AuthenticationService.class);
        controller = new TokenController(mockAuthService);
    }

    @Test
    void getToken_WhenDtoIsGiven_ThenReturnServiceResult() {
        LoginRequestDto mockReq = new LoginRequestDto(MOCK_STRING, MOCK_STRING);
        AccessTokenDto mockResp = new AccessTokenDto();

        Mockito.when(mockAuthService.getToken(MOCK_STRING, MOCK_STRING)).thenReturn(mockResp);

        AccessTokenDto result = controller.getToken(mockReq);

        Assertions.assertEquals(mockResp, result);
    }
}
