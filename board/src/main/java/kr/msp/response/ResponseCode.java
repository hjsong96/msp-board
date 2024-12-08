package kr.msp.response;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum ResponseCode {
	OK(200, "200", "성공"),
	NO_CONTENT(204, "204", "서버 요청은 성공했으나, 클라이언트에 보내줄 데이터가 없음"),
	MISSING_PARAMETER(400, "400", "필수 파라미터를 지정하지 않음"),
    BAD_REQUEST(400, "401", "클라이언트 요청 파라미터 형식이나 내용이 잘못됨"),
    UNAUTHORIZED(401, "402", "요구되는 인증 정보가 누락되었거나 잘못됨"),
    ACCESS_DENIED(403, "403", "요청 리소스에 대한 접근이 제한됨"),
    NOT_FOUND(404, "404", "요청 리소스를 찾을 수 없음"),
    ALREADY_EXIST(409, "405", "요청 리소스가 이미 존재함"),
    SESSION_EXPIRED(401, "406", "세션 만료"),
    DATABASE_ERROR(500, "500", "데이터베이스 오류 발생"),
    INTERNAL_SERVER_ERROR(500, "501", "서버 오류 발생"),
    UNKNOWN(999, "999", "확인 불가");

    private final int httpStatus;
    private final String code;
    private final String message;
	
    ResponseCode(int httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
	
    public static ResponseCode ofHttpStatus(int httpStatus) {
        return Arrays.stream(values())
                .filter(v -> v.httpStatus == httpStatus)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static ResponseCode ofCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}

