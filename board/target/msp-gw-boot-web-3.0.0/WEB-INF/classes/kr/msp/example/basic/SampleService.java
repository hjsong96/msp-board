package kr.msp.example.basic;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import kr.msp.example.basic.dto.User;

@Service
public class SampleService {

	private final SqlSessionTemplate sqlSessionTemplate;

	public SampleService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public Map<String, Object> getSampleUserById(String id) {
		SampleMapper sampleMapper = sqlSessionTemplate.getMapper(SampleMapper.class);
		return sampleMapper.getSampleUserById(id);
	}

	public List<Map<String, Object>> getSampleUserList() {
		SampleMapper sampleMapper = sqlSessionTemplate.getMapper(SampleMapper.class);
		return sampleMapper.getSampleUserList();
	}

	public List<User> getUserList() {
		SampleMapper sampleMapper = sqlSessionTemplate.getMapper(SampleMapper.class);
		return sampleMapper.getUserList();
	}

}
