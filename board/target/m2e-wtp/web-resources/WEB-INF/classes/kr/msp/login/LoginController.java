package kr.msp.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.morpheus.gateway.module.AbstractModule;
import kr.msp.response.Response;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;

@RestController
@RequestMapping("/api/user/login")
public class LoginController extends AbstractModule {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    private final Environment env;

    @Value("${msp.gateway.event-log.enabled:true}")
    private boolean enabled;

    @Autowired
    public LoginController(LoginService loginService, Environment env) {
        this.loginService = loginService;
        this.env = env;
    }

    @PostMapping
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> login(@RequestBody User user, HttpSession session) {
        Map<String, Object> responseMap = new HashMap<>();
    	
    	String userID = user.getUserID();
        Map<String, Object> checkedUser = loginService.findByUserIdAndPassword(user);
        
        ResponseHeader responseHeader = Utils.createResponseHeader(checkedUser);

        if (checkedUser != null && !checkedUser.isEmpty()) {
        	responseMap.putAll(checkedUser);
            logger.info("Login successful for user: {}", userID);
        } else {
            logger.warn("Login failed for user: {}", userID);
            if (checkedUser == null) {
            	checkedUser = new HashMap<>();
            }
            responseMap.putAll(checkedUser);
        }
        responseMap.put("resultCode", responseHeader.getResult_code());
        responseMap.put("resultMsg", responseHeader.getResult_msg());

        return ResponseEntity.status(checkedUser == null ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(new Response<>(responseHeader, responseMap));
    }
}
