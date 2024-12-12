package kr.msp.join;

import org.apache.ibatis.annotations.Mapper;

import kr.msp.dto.CheckIDRequest;
import kr.msp.dto.JoinRequest;
import kr.msp.notUsed.User;

@Mapper
public interface JoinMapper {

	int joinUser(JoinRequest joinRequest);

	int checkUserIdExists(CheckIDRequest checkIDRequest);
}
