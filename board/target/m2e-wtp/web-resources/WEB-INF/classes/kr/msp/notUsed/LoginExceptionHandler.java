package kr.msp.notUsed;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import kr.msp.login.LoginController;
//
//public class LoginExceptionHandler {
//	
//    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
//	
//	@ExceptionHandler(LoginException.class)
//	public LoginErrorResponse handleException(LoginException e, HttpServletRequest request) {
//		logger.error("errorCode : {}, url{}, message:{}", e.getErrorCode(), request.getRequestURI(), e.getErrorMsg());
//		
//		return LoginErrorResponse.builder()
//				.status(e.getErrorCode())
//				.statusMessage(e.getMessage())
//				.build();
//	}
//	
//}


