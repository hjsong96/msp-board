package kr.msp.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.msp.dto.User;

@Mapper
public interface LoginMapper {
	
	Map<String,Object> findByUserId(User user);
}
