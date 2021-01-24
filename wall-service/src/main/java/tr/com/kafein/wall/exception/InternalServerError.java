package tr.com.kafein.wall.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends RuntimeException {

    public InternalServerError(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}