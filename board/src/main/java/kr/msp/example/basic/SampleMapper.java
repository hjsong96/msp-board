package kr.msp.example.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.msp.example.basic.dto.User;

@Mapper
public interface SampleMapper {

    Map<String,Object> getSampleUserById(String id);

    List<Map<String,Object>> getSampleUserList();

    List<User> getUserList();

}
