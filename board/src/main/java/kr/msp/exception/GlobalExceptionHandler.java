package kr.msp.exception;

import java.sql.SQLException;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @ExceptionHandler(NoParameterException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleNoParameterException(NoParameterException e) {
        logger.warn("클라이언트 오류: {}", e.getMessage());
        return Utils.buildBadResponse(ResponseCode.MISSING_PARAMETER);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        logger.warn("클라이언트 오류: {}", e.getMessage());
        
        BindingResult bindingResult = e.getBindingResult();
        
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            
            logger.debug("FieldError - field: {}, message: {}", fieldName, errorMessage);
            
            // @NotNull 오류 처리
            if (errorMessage.contains("널이어서는 안됩니다")) {
                logger.warn("클라이언트 오류 (@NotNull): 필드 {}에 대한 값이 없습니다.", fieldName);
                return Utils.buildBadResponse(ResponseCode.MISSING_PARAMETER);
            }
            
            // @Pattern 오류 처리
            if (errorMessage.contains("일치")) {
                logger.warn("클라이언트 오류 (@Pattern): 필드 {}의 값이 패턴에 맞지 않습니다.", fieldName);
                return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
            }

            // @Size 오류 처리
            if (errorMessage.contains("크기")) {
                logger.warn("클라이언트 오류 (@Size): 필드 {}의 길이가 제한을 초과하거나 미만입니다.", fieldName);
                return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
            }
        }
        
        return Utils.buildBadResponse(ResponseCode.BAD_REQUEST);
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
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleResourceConflict(ResourceConflictException e) {
        logger.error("리소스 충돌 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.ALREADY_EXIST);
    }
    
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleSessionExpiredException(SessionExpiredException e) {
        logger.error("세션만료: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.SESSION_EXPIRED);
    }
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleDataAccessException(DataAccessException e) {
        if (e instanceof DuplicateKeyException) {
            logger.error("리소스 충돌 오류: {}", e.getMessage(), e);
            return Utils.buildBadResponse(ResponseCode.ALREADY_EXIST);
        }
    	
    	logger.error("데이터베이스 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.DATABASE_ERROR);
    }
    
    @ExceptionHandler({SQLException.class, IOException.class, NullPointerException.class})
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleServerError(Exception e) {
        logger.error("서버 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoRowsAffectedException.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleNoRowsAffectedException(NoRowsAffectedException e) {
        logger.error("리소스 반영 없음: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.NO_ROWS_AFFECTED);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> handleGeneralException(Exception e) {
        logger.error("알 수 없는 오류: {}", e.getMessage(), e);
        return Utils.buildBadResponse(ResponseCode.UNKNOWN);
    }
}
