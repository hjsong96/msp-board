package kr.msp.example.basic;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import kr.msp.example.basic.dto.User;
import kr.msp.example.basic.util.PropertiesUtil;

import kr.morpheus.gateway.constant.Const;
import kr.morpheus.gateway.module.AbstractModule;
import kr.morpheus.gateway.protocol.Response;
import kr.morpheus.gateway.protocol.ResponseCode;
import kr.morpheus.gateway.protocol.ResponseHeader;
import kr.morpheus.gateway.response.ResponseFactory;
import kr.morpheus.gateway.support.LocalizedStrings;

@Controller
public class SampleController extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	private final SampleService sampleService;

	private final Environment env;

	@Value("${msp.gateway.event-log.enabled:true}")
	private boolean enabled;

	@Autowired
	public SampleController(SampleService sampleService, Environment env) {
		this.sampleService = sampleService;
		this.env = env;
	}

	/**
	 * 메인 index.jsp 페이지를 호출하는 메서드
	 * @return index.jsp 페이지를 렌더링하는 ModelAndView 객체
	 */
	@GetMapping(value = "/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/**
	 * 사용자 리스트를 Database에서 조회하여 userList.jsp 페이지를 호출하는 메서드
	 * @return 사용자 리스트와 userList.jsp 페이지를 렌더링하는 ModelAndView 객체
	 */
	@GetMapping(value = "/api/basic/sample/userList")
	public ModelAndView getUserList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userList", sampleService.getUserList());
		modelAndView.setViewName("userList");
		return modelAndView;
	}

	/**
	 * 파라미터로 받은 ID로 사용자 정보를 조회하는 메서드
	 * @param id 조회하고자 하는 사용자 ID
	 * @return Map<String, Object> 응답 데이터를 담은 ResponseEntity 객체
	 */
	@GetMapping(value = "/api/basic/sample/{id}")
	public ResponseEntity<Response<ResponseHeader, ?>> getSample(
		@PathVariable String id) {
		Map<String, Object> result = sampleService.getSampleUserById(id);

		if (result == null || result.isEmpty()) {
			return ResponseEntity.ok(
				ResponseFactory.createBadResponse(new ResponseHeader(), result,
					ResponseCode.NoContent, getLocalizedString(LocalizedStrings.NO_CONTENT)).build());
		} else {
			return ResponseEntity.ok(
				ResponseFactory.createSuccessResponse(new ResponseHeader(), result).build());
		}
	}

	/**
	 * SAMPLE_USER 테이블에서 사용자를 조회하여 hashMap으로 받는 메서드
	 * @return List<Map < String, Object>> 응답 데이터를 담은 ResponseEntity 객체
	 */
	@GetMapping(value = "/api/basic/sample/list")
	public ResponseEntity<Response<ResponseHeader, List<Map<String, Object>>>> getSampleList() {
		List<Map<String, Object>> result = sampleService.getSampleUserList();
		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setResultCode(ResponseCode.OK);
		responseHeader.setResultMessage(Const.SUCCESS);
		return ResponseEntity.ok(new Response<>(responseHeader, result));
	}

	/**
	 * SAMPLE_USER 테이블에서 사용자를 조회하여 DTO {@link User} 형태로 받는 메서드
	 * @return List<User> 응답 데이터를 담은 ResponseEntity 객체
	 */
	@GetMapping(value = "/api/basic/sample/dto/list")
	public ResponseEntity<Response<ResponseHeader, List<User>>> getDtoUserList() {
		List<User> result = sampleService.getUserList();
		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setResultCode(ResponseCode.OK);
		responseHeader.setResultMessage(Const.SUCCESS);
		return ResponseEntity.ok(new Response<>(responseHeader, result));
	}

	/**
	 * application.yml 내에 정의되어 있는 변수 값을 호출하는 메서드
	 * @return Map<String, Object> 응답 데이터를 담은 ResponseEntity 객체
	 */
	@GetMapping(value = "/api/basic/sample/config")
	public ResponseEntity<Response<ResponseHeader, Map<String, Object>>> getConfig() {
		Map<String, Object> result = new HashMap<>();

		PropertiesUtil propertiesUtil = new PropertiesUtil(env);
		String value = propertiesUtil.getPropertyName("msp.gateway.event-log.path");
		result.put("path", value);
		result.put("enabled", enabled);

		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setResultCode(ResponseCode.OK);
		responseHeader.setResultMessage(Const.SUCCESS);
		return ResponseEntity.ok(new Response<>(responseHeader, result));
	}

}
