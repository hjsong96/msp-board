package kr.msp.exception;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.JsonSyntaxException;

import io.jsonwebtoken.io.IOException;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("클라이언트 오류: {}", e.getMessage());
        return Utils.buildBadResponse(ResponseCode.MISSING_PARAMETER);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        logger.warn("클라이언트 오류: {}", e.getMessage());
        return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleNoContentException(NoContentException e) {
        logger.warn("사용자 정의 예외: {}", e.getMessage());
        return Utils.buildBadResponse(ResponseCode.NoContent);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleMalformedJson(HttpMessageNotReadableException e) {
    	logger.error("제이슨 오류: {}", e.getMessage(), e);
    	return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(JsonSyntaxException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleJsonSyntaxException(JsonSyntaxException  e) {
    	logger.error("제이슨 오류: {}", e.getMessage(), e);
    	return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleAuthenticationException(AuthenticationException e) {
        logger.error("인증 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.UNAUTHORIZED);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleAccessDeniedException(AccessDeniedException e) {
        logger.error("접근 제한: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.ACCESS_DENIED);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleNotFoundException(NotFoundException e) {
        logger.error("서버 리소스 없음: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.NOT_FOUND);
    }
    
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleResourceConflict(NotFoundException e) {
        logger.error("리소스 충돌 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.ALREADY_EXIST);
    }
    
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleSessionExpiredException(Exception e) {
        logger.error("세션만료: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.SessionExpired);
    }
    
    @ExceptionHandler({SQLException.class, IOException.class, NullPointerException.class})
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleServerError(Exception e) {
        logger.error("서버 오류: {}", e.getMessage(), e);  // 서버 오류 로그
        return Utils.buildBadResponse(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleGeneralException(Exception e) {
        logger.error("알 수 없는 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.UNKNOWN);
    }
    
}
