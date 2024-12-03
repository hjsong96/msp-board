package kr.msp.login;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	
	public LoginService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Autowired
	private JwtUtil jwtUtil;

	public Map<String, Object> findByUserIdAndPassword(User user) {
		LoginMapper loginMapper = sqlSessionTemplate.getMapper(LoginMapper.class);
		return loginMapper.findByUserIdAndPassword(user);
	}

}
