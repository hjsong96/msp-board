package kr.msp.exception;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
    private final String message;
    
    public ResourceConflictException(HttpStatus status, String message) {
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