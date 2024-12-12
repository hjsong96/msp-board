package kr.msp.comment;

import kr.msp.dto.DeleteCommentRequest;
import kr.msp.dto.EditCommentRequest;
import kr.msp.dto.WriteCommentRequest;

public interface CommentMapper {

	int writeComment(WriteCommentRequest writeCommentRequest);

	void plusCommentCount(int boardNo);

	int editComment(EditCommentRequest editCommentRequest);

	int deleteComment(DeleteCommentRequest deleteCommentRequest);

	void minusCommentCount(int boardNo);

}
