package kr.msp.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	
    @Autowired
    private HttpSession session;
    
    // 세션에 속성 추가
    public void setAttribute(String key, Object value) {
        session.setAttribute(key, value);
    }

    // 세션에서 속성 가져오기
    public Object getAttribute(String key) {
        return session.getAttribute(key);
    }

    // 세션 무효화
    public void invalidate() {
        session.invalidate();
    }

}
