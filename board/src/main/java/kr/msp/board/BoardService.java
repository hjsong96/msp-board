package kr.msp.board;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.msp.dto.Board;
import kr.msp.dto.DeleteBoardListRequest;
import kr.msp.dto.DeleteBoardRequest;
import kr.msp.dto.EditBoardRequest;
import kr.msp.dto.WriteBoardRequest;
import kr.msp.exception.AccessDeniedException;
import kr.msp.exception.NoParameterException;
import kr.msp.exception.NoRowsAffectedException;
import kr.msp.exception.NotFoundException;
import kr.msp.login.SessionManager;

@Service
public class BoardService {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	private final SessionManager sessionManage;
	
	public BoardService(SqlSessionTemplate sqlSessionTemplate, SessionManager sessionManager) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.sessionManage = sessionManager;
	}

	public List<Map<String, Object>> getBoardList(int page, int size, int searchType, String searchKeyword) {
		
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

	public void writeBoard(WriteBoardRequest writeBoardRequest) {
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.writeBoard(writeBoardRequest);
		
		if (count == 0) {
			throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
		}

	}

	public void editBoard(EditBoardRequest editBoardRequest) {
		
		int userRank = (int) sessionManage.getAttribute("userRank");
		
		editBoardRequest.setUserRank(userRank);
		System.out.println(userRank);

		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.editBoard(editBoardRequest);
		
		if (count == 0) {
			if (editBoardRequest.getUserRank() == 1) {
				throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
			} else {
				throw new AccessDeniedException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
			}
		}
	}

	public void deleteBoard(DeleteBoardRequest deleteBoardRequest) {
		
		int userRank = (int) sessionManage.getAttribute("userRank");
		deleteBoardRequest.setUserRank(userRank);
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.deleteBoard(deleteBoardRequest);
		
		if (count == 0) {
			if (deleteBoardRequest.getUserRank() == 1) {
				throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
			} else {
				throw new AccessDeniedException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			}
		}
	}

	public void deleteBoardList(DeleteBoardListRequest deleteBoardListRequest) {
		
		int userRank = (int) sessionManage.getAttribute("userRank");
		deleteBoardListRequest.setUserRank(userRank);
		
		BoardMapper boardMapper = sqlSessionTemplate.getMapper(BoardMapper.class);
		int count = boardMapper.deleteBoardList(deleteBoardListRequest);
		
		if (count == 0) {
			if (deleteBoardListRequest.getUserRank() == 1) {
				throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
			} else {
				throw new AccessDeniedException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			}
		}
	}
}
