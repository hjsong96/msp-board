package kr.msp.comment;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.msp.board.BoardMapper;
import kr.msp.dto.DeleteBoardRequest;
import kr.msp.dto.DeleteCommentRequest;
import kr.msp.dto.EditCommentRequest;
import kr.msp.dto.WriteCommentRequest;
import kr.msp.exception.AccessDeniedException;
import kr.msp.exception.NoRowsAffectedException;
import kr.msp.login.SessionManager;

@Service
public class CommentService{
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private final SqlSessionTemplate sqlSessionTemplate;
	private final SessionManager sessionManage;
	
	public CommentService (SqlSessionTemplate sqlSessionTemplate, SessionManager sessionManage) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.sessionManage = sessionManage;
	}

	public void writeComment(WriteCommentRequest writeCommentRequest) {
		
		String userID = (String) sessionManage.getAttribute("userID");
		writeCommentRequest.setUserID(userID);
		
		CommentMapper commentMapper = sqlSessionTemplate.getMapper(CommentMapper.class);
		int count = commentMapper.writeComment(writeCommentRequest);
		
		if (count == 0) {
			throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
		} else {
			commentMapper.plusCommentCount(writeCommentRequest.getBoardNo());
		}
	}

	public void editComment(EditCommentRequest editCommentRequest) {
		
		String userID = (String) sessionManage.getAttribute("userID");
		int userRank = (int) sessionManage.getAttribute("userRank");
		
		editCommentRequest.setUserID(userID);
		editCommentRequest.setUserRank(userRank);
		
		CommentMapper commentMapper = sqlSessionTemplate.getMapper(CommentMapper.class);
		int count = commentMapper.editComment(editCommentRequest);
		
		if (count == 0) {
			if (editCommentRequest.getUserRank() == 1) {
				throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
			} else {
				throw new AccessDeniedException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
			}
		}
	}
	
	public void deleteComment(DeleteCommentRequest deleteCommentRequest) {
		
		String userID = (String) sessionManage.getAttribute("userID");
		int userRank = (int) sessionManage.getAttribute("userRank");
		
		deleteCommentRequest.setUserID(userID);
		deleteCommentRequest.setUserRank(userRank);
		
		CommentMapper commentMapper = sqlSessionTemplate.getMapper(CommentMapper.class);
		int count = commentMapper.deleteComment(deleteCommentRequest);
		
		if (count == 0) {
			if (deleteCommentRequest.getUserRank() == 1) {
				throw new NoRowsAffectedException(HttpStatus.INTERNAL_SERVER_ERROR, "요청 리소스에 변화가 반영되지 않음");
			} else {
				throw new AccessDeniedException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			}
		}
		
		commentMapper.minusCommentCount(deleteCommentRequest.getBoardNo());
	}
}