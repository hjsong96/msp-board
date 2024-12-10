package kr.msp.login;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.msp.dto.LoginRequest;
import kr.msp.dto.User;
import kr.msp.exception.NoParameterException;
import kr.msp.exception.NotFoundException;
import kr.msp.notUsed.NoContentException;

@Service
public class LoginService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final PasswordEncoder passwordEncoder;
    
	public LoginService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
    
    public Map<String, Object> findByUserIdAndPassword(LoginRequest loginRequest) {
        
        LoginMapper loginMapper = sqlSessionTemplate.getMapper(LoginMapper.class);
        Map<String, Object> userMap = loginMapper.findByUserId(loginRequest);

        if (userMap == null || userMap.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        
        String storedPassword = (String) userMap.get("userPW");
        if (!passwordEncoder.matches(loginRequest.getUserPW(), storedPassword)) {
        	throw new NotFoundException(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다.");
		}
        
        userMap.remove("userPW");
        
        return userMap;
    }
}
