package kr.msp.join;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.msp.dto.User;
import kr.msp.exception.NoParameterException;
import kr.msp.exception.ResourceConflictException;

@Service
public class JoinService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
	private final PasswordEncoder passwordEncoder;
	
	public JoinService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public void joinUser(User user) {
		
		if (user.getUserID() == null || user.getUserPW() == null || user.getUserName() == null) {
			throw new NoParameterException(HttpStatus.BAD_REQUEST , "필수 파라미터를 지정하지 않았습니다.");
		}
		
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getUserPW());
		user.setUserPW(encodedPassword);
		
		JoinMapper joinMapper = sqlSessionTemplate.getMapper(JoinMapper.class);
		joinMapper.joinUser(user);

	}

	public void checkUserIdExists(User user) {
		
		if (user.getUserID() == null) {
			throw new NoParameterException(HttpStatus.BAD_REQUEST, "필수 파라미터를 지정하지 않았습니다.");
		}
		
		JoinMapper joinMapper = sqlSessionTemplate.getMapper(JoinMapper.class);
		int count = joinMapper.checkUserIdExists(user);
		
		if (count > 0) {
			throw new ResourceConflictException(HttpStatus.CONFLICT, "요청 리소스가 이미 존재합니다.");
		}
	}

}
