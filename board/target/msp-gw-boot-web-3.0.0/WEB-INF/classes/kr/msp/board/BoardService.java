package kr.msp.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	
	public BoardService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Board> getBoardList(String userID) {
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		return boardMapper.getBoardList(userID);
	}

}
