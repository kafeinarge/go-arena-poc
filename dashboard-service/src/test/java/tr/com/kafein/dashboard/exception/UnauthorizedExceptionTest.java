package tr.com.kafein.dashboard.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tr.com.kafein.dashboard.TestConstants.MOCK_STRING;

class UnauthorizedExceptionTest {

    @Test
    void getStatus_returnUnauthorized() {
        UnauthorizedException ex = new UnauthorizedException(MOCK_STRING);

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
        assertEquals(MOCK_STRING, ex.getMessage());
    }
}
