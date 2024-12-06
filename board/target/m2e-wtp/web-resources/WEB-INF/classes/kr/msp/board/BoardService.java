package kr.msp.board;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.msp.exception.NotFoundException;

@Service
public class BoardService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	
	public BoardService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Map<String, Object>> getBoardList() {
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		List<Map<String, Object>> boardList = boardMapper.getBoardList();
		
		if (boardList == null || boardList.isEmpty()) {
			throw new NotFoundException(HttpStatus.NOT_FOUND, "보드리스트를 찾을 수 없습니다.");
		}
		return boardList;
	}
}
