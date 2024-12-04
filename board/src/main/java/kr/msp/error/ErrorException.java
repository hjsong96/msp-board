package kr.msp.error;

public class ErrorException extends RuntimeException {
	
	private final ErrorCode errorCode;
	
	public ErrorException(ErrorCode errorCode) {
		super(errorCode.getResult_msg());
		this.errorCode = errorCode;
	}
	
    public ErrorCode getErrorCode() {
        return errorCode;
    }
	
}
