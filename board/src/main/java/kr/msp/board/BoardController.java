package kr.msp.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.morpheus.gateway.module.AbstractModule;
import kr.morpheus.gateway.protocol.Request;
import kr.morpheus.gateway.protocol.RequestHeader;
import kr.msp.dto.BoardDetailRequest;
import kr.msp.dto.BoardListRequest;
import kr.msp.dto.DeleteBoardListRequest;
import kr.msp.dto.DeleteBoardRequest;
import kr.msp.dto.EditBoardRequest;
import kr.msp.dto.LoginRequest;
import kr.msp.dto.WriteBoardRequest;
import kr.msp.notUsed.Board;
import kr.msp.notUsed.User;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@Controller
@RequestMapping("/api")
public class BoardController extends AbstractModule {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	private final Environment env;
	
	@Value("${msp.gateway.event-log.enabled:true}")
	private boolean enabled;

	@Autowired
	public BoardController(BoardService boardService, Environment env) {
		this.boardService = boardService;
		this.env = env;
	}
	
	@GetMapping("/boardList")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("boardList");
		return modelAndView;
	}
	
	@PostMapping("/boardList")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> getBoardList(@RequestBody @Valid Request<RequestHeader, BoardListRequest> request) {
		
		int page = request.getBody().getPage();
		int size = request.getBody().getSize();
		int searchType = request.getBody().getSearchType();
		String searchKeyword = request.getBody().getSearchKeyword();
		
		List<Map<String, Object>> boardList = boardService.getBoardList(page, size, searchType, searchKeyword);
		int totalPages = boardService.getBoardTotalCount(size, searchType, searchKeyword);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("boardList", boardList);
		responseMap.put("page", page);
		responseMap.put("size", size);
		responseMap.put("searchType", searchType);
		responseMap.put("searchKeyword", searchKeyword);
		responseMap.put("totalPages", totalPages);
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/board/write")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> writeBoard(@RequestBody @Valid Request<RequestHeader, WriteBoardRequest> request) {
		
		Map<String,Object> responseMap = new HashMap<>();
		boardService.writeBoard(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/board/edit")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> editBoard(@RequestBody @Valid Request<RequestHeader, EditBoardRequest> request) {
		
		Map<String,Object> responseMap = new HashMap<>();
		boardService.editBoard(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/board/delete")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> deleteBoard(@RequestBody @Valid Request<RequestHeader, DeleteBoardRequest> request) {
		
		Map<String,Object> responseMap = new HashMap<>();
		boardService.deleteBoard(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/boardList/delete")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> deleteBoardList(@RequestBody @Valid Request<RequestHeader, DeleteBoardListRequest> request) {
		
		Map<String,Object> responseMap = new HashMap<>();
		boardService.deleteBoardList(request.getBody());
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/board/detail")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> boardDetail(@RequestBody @Valid Request<RequestHeader, BoardDetailRequest> request) {
		
		int size = request.getBody().getSize();
		
		Map<String,Object> responseMap = new HashMap<>();
		Map<String, Object> board = boardService.findBoardByBoardNo(request.getBody());
	
		List<Map<String, Object>> commentList = boardService.findCommentListByBoardNo(request.getBody(), size);
		
		responseMap.put("board", board);
		responseMap.put("commentList", commentList);
		responseMap.put("size", size);
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	
}
