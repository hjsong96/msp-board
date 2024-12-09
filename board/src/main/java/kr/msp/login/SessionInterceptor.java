package kr.msp.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.msp.exception.SessionExpiredException;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	
    @Autowired
    private SessionManager sessionManage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	HttpSession session = request.getSession(false);
    	
        String requestURI = request.getRequestURI();
        // 회원가입, 아이디 중복체크 세션 체크 제외
        if (requestURI.startsWith("/api/user/join") || requestURI.startsWith("/api/user/checkID") || requestURI.startsWith("/api/user/login")) {
        	return true;
        }
        
        // 세션 유효성 검사
        if (sessionManage.getAttribute("userID") == null) {
        	throw new SessionExpiredException(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다.");
        }
        return true;
    }
    
}
