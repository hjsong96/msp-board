package kr.msp.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.msp.dto.Board;
import kr.msp.dto.BoardRequest;
import kr.msp.dto.User;
import kr.msp.exception.NoParameterException;
import kr.msp.exception.NoRowsAffectedException;
import kr.msp.exception.NotFoundException;

@Service
public class BoardService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	
	public BoardService(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Map<String, Object>> getBoardList(BoardRequest boardRequest) {
		
		int page = boardRequest.getPage();
		int size = boardRequest.getSize();
		int searchType = boardRequest.getSearchType();
		String searchKeyword = boardRequest.getSearchKeyword();
		
		int offset = (page - 1) * size;
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		List<Map<String, Object>> boardList = boardMapper.getBoardList(offset, size, searchType, searchKeyword);
		
		if (boardList == null || boardList.isEmpty()) {
			throw new NotFoundException(HttpStatus.NOT_FOUND, "보드리스트를 찾을 수 없습니다.");
		}
		
		return boardList;
	}

	public int getBoardTotalCount(int size, int searchType, String searchKeyword) {
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		
        int totalCount = boardMapper.getBoardTotalCount(searchType, searchKeyword);
        return (int) Math.ceil((double) totalCount / size);
	}

	public void writeBoard(Board board) {
		
		if (board.getBoardTitle() == null || board.getBoardType() < 0 || board.getBoardContent() == null || board.getUserID() == null) {
            throw new NoParameterException(HttpStatus.BAD_REQUEST, "필수 파라미터를 지정하지 않음");
		}
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.writeBoard(board);
		
		if (count == 0) {
			throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
		}

	}

	public void editBoard(Board board) {
		
		if (board.getBoardNo() <0 || board.getBoardTitle() == null || board.getBoardType() < 0 || board.getBoardContent() == null || board.getUserID() == null) {
			throw new NoParameterException(HttpStatus.BAD_REQUEST, "필수 파라미터를 지정하지 않음");
		}
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.editBoard(board);
		
		if (count == 0) {
			throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
		}
		
	}
}
