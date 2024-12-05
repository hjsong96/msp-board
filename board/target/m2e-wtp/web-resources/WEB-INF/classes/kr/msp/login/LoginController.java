package kr.msp.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.morpheus.gateway.module.AbstractModule;
import kr.msp.exception.CustomException;
import kr.msp.exception.TypeErrorException;
import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
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
    public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> login(@Valid @RequestBody User user, HttpSession session) {
        Map<String, Object> responseMap = new HashMap<>();
        
        try {
            if (user.getUserID() == null || user.getUserPW() == null) {
                throw new IllegalArgumentException("필수 파라미터를 지정하지 않음");
            }
            
            Map<String, Object> checkedUser = loginService.findByUserIdAndPassword(user);

            if (checkedUser == null) {
                throw new CustomException (HttpStatus.NO_CONTENT, "서버 요청은 성공했으나, 클라이언트에 보내줄 데이터가 없음");
            }
            
            // 로그인 성공
            responseMap.putAll(checkedUser);
            return Utils.buildOkResponse(ResponseCode.OK, responseMap);

        } catch (IllegalArgumentException e) {
        	logger.warn("필수 파라미터 없음: {}", e.getMessage());
        	return Utils.buildBadResponse(ResponseCode.MISSING_PARAMETER, responseMap);
        } catch (CustomException e) {
        	logger.warn("데이터 없음: {}", e.getMessage());
        	return Utils.buildBadResponse(ResponseCode.NoContent, responseMap);
        } catch (Exception e) {
        	logger.warn("서버 오류: {}", e.getMessage());
        	return Utils.buildBadResponse(ResponseCode.INTERNAL_SERVER_ERROR, responseMap);
        }
    }

}
