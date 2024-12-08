package kr.msp.join;

import org.apache.ibatis.annotations.Mapper;

import kr.msp.dto.User;

@Mapper
public interface JoinMapper {

	int joinUser(User user);

	int checkUserIdExists(User user);
}
