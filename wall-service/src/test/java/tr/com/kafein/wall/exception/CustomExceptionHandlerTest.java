package tr.com.kafein.wall.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tr.com.kafein.wall.dto.ErrorDto;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;
import static tr.com.kafein.wall.util.Constants.TYPE_MISMATCH_MSG;

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
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(MOCK_STRING, new HttpInputMessage() {
            @Override
            public InputStream getBody() {
                return InputStream.nullInputStream();
            }

            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        });

        ErrorDto errorDto = ErrorDto.builder()
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .result(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errorMessage(ex.getMessage())
                .build();

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

    @Test
    void handleTypeMismatch() {
        MethodArgumentTypeMismatchException mockException = mock(MethodArgumentTypeMismatchException.class);
        WebRequest mockRequest = mock(WebRequest.class);

        when(mockException.getName()).thenReturn(MOCK_STRING);
        when(mockException.getValue()).thenReturn(MOCK_STRING);

        ResponseEntity<Object> result = exceptionHandler.handleTypeMismatch(mockException, mockRequest);
        ErrorDto resultBody = (ErrorDto)result.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), resultBody.getResultCode());
        assertEquals(String.format(TYPE_MISMATCH_MSG, MOCK_STRING, MOCK_STRING, null), resultBody.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.name(), resultBody.getResult());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void handleNotFoundException() {
        NotFoundException ex = new NotFoundException(MOCK_STRING);
        WebRequest mockRequest = mock(WebRequest.class);

        ResponseEntity<Object> result = exceptionHandler.handleNotFoundException(ex, mockRequest);
        ErrorDto resultBody = (ErrorDto) result.getBody();

        assertEquals(HttpStatus.NOT_FOUND.value(), resultBody.getResultCode());
        assertEquals(MOCK_STRING, resultBody.getErrorMessage());
        assertEquals(HttpStatus.NOT_FOUND.name(), resultBody.getResult());
        assertTrue(result.getHeaders().isEmpty());
    }

    @Test
    void handleInternalServerError() {
        InternalServerError ex = new InternalServerError(MOCK_STRING);

        WebRequest mockRequest = mock(WebRequest.class);

        ResponseEntity<Object> result = exceptionHandler.handleInternalServerError(ex, mockRequest);
        ErrorDto resultBody = (ErrorDto) result.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultBody.getResultCode());
        assertEquals(MOCK_STRING, resultBody.getErrorMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), resultBody.getResult());
        assertTrue(result.getHeaders().isEmpty());
    }
}
