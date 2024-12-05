package kr.msp.exception;

import org.springframework.http.HttpStatus;

public class TypeErrorException extends RuntimeException{

    private final HttpStatus status;
    private final String message;

    public TypeErrorException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
