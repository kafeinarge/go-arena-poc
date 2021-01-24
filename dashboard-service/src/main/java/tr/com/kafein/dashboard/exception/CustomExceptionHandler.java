package tr.com.kafein.dashboard.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tr.com.kafein.dashboard.dto.ErrorDto;


@ControllerAdvice(basePackages = {"tr.com.kafein.dashboard.controller"})
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

}