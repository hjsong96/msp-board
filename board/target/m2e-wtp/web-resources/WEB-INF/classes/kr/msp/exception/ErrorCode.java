//package kr.msp.exception;
//
//import lombok.Getter;
//
//@Getter
//public enum ErrorCode {
//	
//	NoContent("서버 요청은 성공했으나, 클라이언트에 보내줄 데이터가 없음"),
//    BAD_REQUEST("클라이언트 요청의 형식이나 내용이 잘못됨"),
//    INVALID_PARAMETER("요청 파라미터의 형식이나 내용에 오류가 있음"),
//    MISSING_PARAMETER("필수 파라미터를 지정하지 않음"),
//    LIMIT_EXCEEDED("파라미터 제한을 초과함"),
//    OUT_OF_RANGE("파라미터가 허용 범위를 벗어남"),
//	UNAUTHORIZED("요구되는 인증 정보가 누락되었거나 잘못됨"),
//    FORBIDDEN("클라이언트 요청이 거부됨"),
//    ACCESS_DENIED( "요청 리소스에 대한 접근이 제한됨"),
//    NOT_FOUND("요청 리소스를 찾을 수 없음"),
//    RESOURCE_NOT_EXIST("특정 리소스를 찾을 수 없음"),
//    CONFLICT("요청 리소스와 충돌 발생"),
//    ALREADY_EXIST("요청 리소스가 이미 존재함"),
//    INTERNAL_SERVER_ERROR("서버 내부 오류 발생"),
//    SERVICE_UNAVAILABLE("일시적인 서버 오류 발생"),
//    UNKNOWN("확인 불가");
//	
//	private final String message;
//	
//	ErrorCode(String message) {
//		this.message = message;
//	}
//	
//	
//
//}
