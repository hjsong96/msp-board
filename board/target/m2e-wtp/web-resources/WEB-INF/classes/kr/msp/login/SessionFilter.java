package kr.msp.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import kr.msp.exception.SessionExpiredException;

@WebFilter(urlPatterns = "/api/*") // 모든 API 요청에 대해 세션 검사
public class SessionFilter implements Filter {

    @Autowired
    private SessionManager sessionManage;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 회원가입, 아이디 중복체크 세션 체크 제외
        if (requestURI.startsWith("/api/user/join") || requestURI.startsWith("/api/user/checkID")) {
            chain.doFilter(request, response); 
            return;
        }

        // 세션 검사
        HttpSession session = httpRequest.getSession(false);
        if (session == null || sessionManage.getAttribute("userID") == null) {
            httpResponse.sendRedirect("/api/user/login");  // /login 페이지로 리디렉션
            return;
//        	throw new SessionExpiredException(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다.");
        }
        chain.doFilter(request, response); // 세션 유효한 경우 다음 필터로 진행
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
