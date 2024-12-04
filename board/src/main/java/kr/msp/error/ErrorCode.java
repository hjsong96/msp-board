package kr.msp.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
    SERVER_ERROR(500, "998", "서버 오류입니다."),
    DUPLICATE_ID(400, "997", "아이디 중복입니다."),
    NOT_CORRECT_ID(400, "997", "아이디가 일치하지 않습니다."),
    NOT_CORRECT_PW(400, "997", "패스워드가 일치하지 않습니다."),
    INVALID_INPUT_VALUE(400, "999", "유효성 검증에 실패했습니다."),
    ACCOUNT_NOT_FOUND(404, "995", "계정을 찾을 수 없습니다.");
	
	private final int status;
	private final String result_code;
	private final String result_msg;
	
	ErrorCode(int status, String result_code, String result_msg) {
		this.status = status;
		this.result_code = result_code;
		this.result_msg = result_msg;
	}

}
