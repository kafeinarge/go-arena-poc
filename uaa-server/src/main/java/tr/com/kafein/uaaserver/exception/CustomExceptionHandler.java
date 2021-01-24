package tr.com.kafein.uaaserver.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tr.com.kafein.uaaserver.dto.ErrorDto;


@ControllerAdvice(basePackages = {"tr.com.kafein.uaaserver.controller"})
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

    @ExceptionHandler
    protected ResponseEntity<Object> handleInternalServerError(UnauthorizedException ex, WebRequest request) {
        ErrorDto dto = new ErrorDto();
        dto.setResultCode(ex.getStatus().value());
        dto.setErrorMessage(ex.getMessage());
        dto.setResult(ex.getStatus().name());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }

}