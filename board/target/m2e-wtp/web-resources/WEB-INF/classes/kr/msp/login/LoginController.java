package kr.msp.login;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.morpheus.gateway.module.AbstractModule;
import kr.morpheus.gateway.protocol.Response;
import kr.morpheus.gateway.protocol.ResponseCode;
import kr.morpheus.gateway.protocol.ResponseHeader;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController extends AbstractModule {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final LoginService loginService;
	private final Environment env;
	private final JwtUtil jwtUtil;

	@Value("${msp.gateway.event-log.enabled:true}")
	private boolean enabled;

	@Autowired
	public LoginController(LoginService loginService, Environment env, JwtUtil jwtUtil) {
		this.loginService = loginService;
		this.env = env;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@PostMapping
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> authenticateUser(@RequestBody User user) {
		
		String userID = user.getUserID();
		
		Map<String, Object> checkedUser = loginService.findByUserIdAndPassword(user);
		
		if (checkedUser != null && !checkedUser.isEmpty()) {
			int userRank = (int) checkedUser.get("userRank");
			String jwt = jwtUtil.generateToken(userID, userRank);
			checkedUser.put("token", jwt);
			ResponseHeader responseHeader = new ResponseHeader(ResponseCode.OK, "사용자 조회 완료");
			
			logger.info("Login successful for user: {}", userID);
			return ResponseEntity.ok(new Response<>(responseHeader, checkedUser));
		} else {
			ResponseHeader responseHeader = new ResponseHeader(ResponseCode.Unauthorized, "잘못된 사용자 정보");
			
			logger.warn("Login failed for user: {}", userID);  // 로그인 실패 로그
	        Response<ResponseHeader, Map<String, Object>> errorResponse = new Response<>(responseHeader, null);
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}
}
