package kr.msp.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@Controller
@RequestMapping("/api/boardList")
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
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("boardList");
		return modelAndView;
	}
	
	@PostMapping
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> getBoardList() {
		List<Map<String, Object>> boardList = boardService.getBoardList();
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("boardList", boardList);
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	
}
