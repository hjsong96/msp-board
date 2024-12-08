package kr.msp.notUsed;
//package kr.msp.login;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import kr.msp.exception.SessionExpiredException;
//
//@Component
//@WebFilter(urlPatterns = "/api/*") // 모든 API 요청에 대해 세션 검사
//public class SessionFilter implements Filter {
//
//    @Autowired
//    private SessionManager sessionManage;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String requestURI = httpRequest.getRequestURI();
//
//        // 회원가입, 아이디 중복체크 세션 체크 제외
//        if (requestURI.startsWith("/api/user/join") || requestURI.startsWith("/api/user/checkID") || requestURI.startsWith("/api/user/login")) {
//            chain.doFilter(request, response); 
//            return;
//        }
//
//        // 세션 검사
//        HttpSession session = httpRequest.getSession(false);
//        System.out.println("세션 여부 체크" + session);
//        if (session == null || sessionManage.getAttribute("userID") == null) {
//        	throw new SessionExpiredException(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다.");
//            //httpResponse.sendRedirect("/api/user/login");  // /login 페이지로 리디렉션
//            //return;
//        }
//        chain.doFilter(request, response); // 세션 유효한 경우 다음 필터로 진행
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void destroy() {}
//}
