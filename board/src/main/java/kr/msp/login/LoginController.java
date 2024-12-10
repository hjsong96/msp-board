package kr.msp.login;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import kr.morpheus.gateway.module.AbstractModule;
import kr.msp.dto.LoginRequest;
import kr.msp.dto.User;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@Controller
@RequestMapping("/api/user")
public class LoginController extends AbstractModule {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    private final LoginService loginService;
    private final Environment env;
    private final SessionManager sessionManage;

    @Value("${msp.gateway.event-log.enabled:true}")
    private boolean enabled;

    @Autowired
    public LoginController(LoginService loginService, Environment env, SessionManager sessionManager) {
        this.loginService = loginService;
        this.env = env;
        this.sessionManage = sessionManager;
    }
    
	@GetMapping(value = "/login")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

    @PostMapping("/login")
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        
    	//비즈니스 로직
        Map<String, Object> checkedUser = loginService.findByUserIdAndPassword(loginRequest);
        
        // 로그인 성공
        responseMap.putAll(checkedUser);
        sessionManage.setAttribute("userID", checkedUser.get("userID"));
        sessionManage.setAttribute("userRank", checkedUser.get("userRank"));
        
        return Utils.buildOkResponse(ResponseCode.OK, responseMap);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> logout() {
    	sessionManage.invalidate();
    	
    	Map<String, Object> responseMap = new HashMap<>();
    	
    	return Utils.buildOkResponse(ResponseCode.OK, responseMap);
    }
    
    
}
