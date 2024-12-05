package kr.msp.board;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.jsonwebtoken.Claims;
import kr.morpheus.gateway.module.AbstractModule;
import kr.msp.login.CookieUtils;
import kr.msp.login.JwtUtil;

@RestController
@RequestMapping("/api")
public class BoardController extends AbstractModule {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	private final Environment env;
	private final JwtUtil jwtUtil;
	
	@Value("${msp.gateway.event-log.enabled:true}")
	private boolean enabled;

	@Autowired
	public BoardController(BoardService boardService, Environment env, JwtUtil jwtUtil) {
		this.boardService = boardService;
		this.env = env;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/boardList")
	public ModelAndView getBoardList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String token = CookieUtils.getCookie(request, "token");
		
	    if (token == null) {
	        modelAndView.setViewName("redirect:/api/auth/login");
	        return modelAndView;
	    }
	    
	    boolean isAdmin = false;
	    String userID = null;
	    
	    try {
	        // 토큰에서 userID와 role 추출
	        userID = jwtUtil.getUserID(token);
	        Integer role = jwtUtil.getUserRole(token);
	        isAdmin = (role != null && role == 1);  // role이 1이면 관리자
	    } catch (Exception e) {
	        isAdmin = false;
	        userID = null;  // 예외 발생 시 userID도 null로 처리
	    }
		
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("boardList", boardService.getBoardList(userID));
		modelAndView.setViewName("boardList");
		return modelAndView;
	}
}
