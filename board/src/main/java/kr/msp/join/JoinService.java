package kr.msp.join;

import javax.validation.Valid;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.msp.dto.CheckIDRequest;
import kr.msp.dto.JoinRequest;
import kr.msp.exception.NoParameterException;
import kr.msp.exception.ResourceConflictException;
import kr.msp.notUsed.User;

@Service
public class JoinService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
	private final PasswordEncoder passwordEncoder;
	
	public JoinService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public void checkUserIdExists(@Valid CheckIDRequest checkIDRequest) {
		
		JoinMapper joinMapper = sqlSessionTemplate.getMapper(JoinMapper.class);
		int count = joinMapper.checkUserIdExists(checkIDRequest);
		
		if (count > 0) {
			throw new ResourceConflictException(HttpStatus.CONFLICT, "요청 리소스가 이미 존재합니다.");
		}
	}
	
	public void joinUser(JoinRequest joinRequset) {
		
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(joinRequset.getUserPW());
		joinRequset.setUserPW(encodedPassword);
		
		JoinMapper joinMapper = sqlSessionTemplate.getMapper(JoinMapper.class);
		joinMapper.joinUser(joinRequset);
	}

}
