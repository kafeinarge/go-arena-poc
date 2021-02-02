package tr.com.kafein.dashboard.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import tr.com.kafein.dashboard.dto.ErrorDto;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static tr.com.kafein.dashboard.Constants.MOCK_MESSAGE;

class CustomExceptionHandlerTest {
    private CustomExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CustomExceptionHandler();
    }

    @Test
    void handleHttpMessageNotReadable_WhenStaticErrorMessageIsGiven_ThenReturnExpectedResponse() {
        HttpHeaders headers = new HttpHeaders();
        WebRequest request = mock(WebRequest.class);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(MOCK_MESSAGE, new HttpInputMessage() {
            @Override
            public InputStream getBody() {
                return InputStream.nullInputStream();
            }

            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        });

        ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage());

        ResponseEntity<Object> expectedResult = new ResponseEntity<>(errorDto, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<Object> result = exceptionHandler.handleHttpMessageNotReadable(ex, headers, status, request);

        assertEquals(expectedResult.getHeaders(), result.getHeaders());
        assertEquals(expectedResult.getStatusCode(), result.getStatusCode());

        ErrorDto exceptedBody = (ErrorDto) expectedResult.getBody();
        ErrorDto resultBody = (ErrorDto) result.getBody();
        assertEquals(exceptedBody.getErrorMessage(), resultBody.getErrorMessage());
        assertEquals(exceptedBody.getResult(), resultBody.getResult());
        assertEquals(exceptedBody.getResultCode(), resultBody.getResultCode());
    }
}
