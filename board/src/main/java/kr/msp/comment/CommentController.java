package kr.msp.comment;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.morpheus.gateway.protocol.Request;
import kr.morpheus.gateway.protocol.RequestHeader;
import kr.msp.board.BoardController;
import kr.msp.board.BoardService;
import kr.msp.dto.DeleteCommentRequest;
import kr.msp.dto.EditCommentRequest;
import kr.msp.dto.WriteCommentRequest;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@Controller
@RequestMapping("/api/board/comment")
public class CommentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private final CommentService commentService;
	private final Environment env;
	
	@Autowired
	public CommentController(CommentService commentService, Environment env) {
		this.commentService = commentService;
		this.env = env;
	}
	
	@PostMapping("/write")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> writeComment(@RequestBody @Valid Request<RequestHeader, WriteCommentRequest> request) {
		
		Map<String, Object> responseMap = new HashMap<>();
		commentService.writeComment(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> editComment(@RequestBody @Valid Request<RequestHeader, EditCommentRequest> request) {
		
		Map<String, Object> responseMap = new HashMap<>();
		commentService.editComment(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> deleteComment(@RequestBody @Valid Request<RequestHeader, DeleteCommentRequest> request) {
		
		Map<String, Object> responseMap = new HashMap<>();
		commentService.deleteComment(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}

}
