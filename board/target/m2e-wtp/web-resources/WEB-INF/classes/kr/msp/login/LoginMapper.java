package kr.msp.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	
	Map<String,Object> findByUserIdAndPassword(User user);
}
