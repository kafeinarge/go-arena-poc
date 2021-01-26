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


@ControllerAdvice(basePackages = {"tr.com.kafein.wall.controller"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto dto = new ErrorDto();
        dto.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        dto.setErrorMessage(ex.getMessage());
        dto.setResult(HttpStatus.INTERNAL_SERVER_ERROR.name());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        Object value = ex.getValue();

        ErrorDto dto = new ErrorDto();
        dto.setResultCode(HttpStatus.BAD_REQUEST.value());
        dto.setErrorMessage("Bu endpoint için " + name + " değeri '" + value + "' gönderilmiştir " +
                "ancak beklenilen tip " + type + " dır;");
        dto.setResult(HttpStatus.BAD_REQUEST.name());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDto dto = new ErrorDto();
        dto.setResultCode(ex.getStatus().value());
        dto.setErrorMessage(ex.getMessage());
        dto.setResult(ex.getStatus().name());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleInternalServerError(InternalServerError ex, WebRequest request) {
        ErrorDto dto = new ErrorDto();
        dto.setResultCode(ex.getStatus().value());
        dto.setErrorMessage(ex.getMessage());
        dto.setResult(ex.getStatus().name());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }
}