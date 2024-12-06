package kr.msp.login;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.msp.dto.User;
import kr.msp.exception.NoContentException;
import kr.msp.exception.NotFoundException;

@Service
public class LoginService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
	public LoginService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
    
    public Map<String, Object> findByUserIdAndPassword(User user) {
        if (user.getUserID() == null || user.getUserPW() == null) {
            throw new IllegalArgumentException("필수 파라미터를 지정하지 않음");
        }
        
        LoginMapper loginMapper = sqlSessionTemplate.getMapper(LoginMapper.class);
        Map<String, Object> userMap = loginMapper.findByUserIdAndPassword(user);

        if (userMap == null || userMap.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        return userMap;
    }
}
