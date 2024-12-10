package kr.msp.join;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.msp.dto.CheckIDRequest;
import kr.msp.dto.JoinRequest;
import kr.msp.dto.User;
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
    
	@PostMapping("/checkID")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> checkID(@RequestBody @Valid CheckIDRequest checkIDRequest) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		joinService.checkUserIdExists(checkIDRequest);
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	
	@PostMapping("/join")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> joinUser(@RequestBody @Valid JoinRequest joinRequset) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		joinService.joinUser(joinRequset);
		
		return Utils.buildOkResponse(ResponseCode.OK, responseMap);
	}
	


}
