package kr.msp.join;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.msp.login.LoginService;
import kr.msp.login.SessionManager;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;
import kr.msp.util.Utils;


@RestController
@RequestMapping("/api/user")
public class JoinController {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
	
    private final JoinService joinService;
    private final Environment env;
    
    @Autowired
    public JoinController(JoinService joinService, Environment env) {
        this.joinService = joinService;
        this.env = env;
    }
	
	@PostMapping("/join")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> join() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/checkID")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> checkID() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}

}
