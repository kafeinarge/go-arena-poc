package tr.com.kafein.wall.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tr.com.kafein.wall.dto.ErrorDto;

import static tr.com.kafein.wall.util.Constants.TYPE_MISMATCH_MSG;


@ControllerAdvice(basePackages = {"tr.com.kafein.wall.controller"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto dto = ErrorDto.builder()
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .result(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String name = ex.getName();

        String type = null;
        if (ex.getRequiredType() != null) {
            type = ex.getRequiredType().getSimpleName();
        }
        Object value = ex.getValue();

        ErrorDto dto = ErrorDto.builder()
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(String.format(TYPE_MISMATCH_MSG, name, value, type))
                .result(HttpStatus.BAD_REQUEST.name())
                .build();
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDto dto = ErrorDto.builder()
                .resultCode(ex.getStatus().value())
                .errorMessage(ex.getMessage())
                .result(ex.getStatus().name())
                .build();
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleInternalServerError(InternalServerError ex, WebRequest request) {
        ErrorDto dto = ErrorDto.builder()
                .resultCode(ex.getStatus().value())
                .errorMessage(ex.getMessage())
                .result(ex.getStatus().name())
                .build();
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }
}
