package tr.com.kafein.wall.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;

class BadRequestExceptionTest {

    @Test
    void getStatusAndMessage() {
        BadRequestException ex = new BadRequestException(MOCK_STRING);

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(MOCK_STRING, ex.getMessage());
    }
}
