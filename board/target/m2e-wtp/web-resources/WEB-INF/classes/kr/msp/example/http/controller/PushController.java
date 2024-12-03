package kr.msp.example.http.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

import kr.msp.example.http.HttpProperties;
import kr.msp.example.http.service.PushService;
import kr.msp.example.http.dto.PushResult;
import kr.msp.example.http.dto.PushSendResult;
import kr.msp.example.http.dto.RequestParameter;

import kr.morpheus.gateway.protocol.Request;
import kr.morpheus.gateway.protocol.RequestHeader;
import kr.morpheus.gateway.protocol.Response;
import kr.morpheus.gateway.protocol.ResponseHeader;
import kr.morpheus.gateway.response.ResponseFactory;

@Controller
@Slf4j
public class PushController {

	private final PushService pushService;
	private static String serverUrl = "";

	public PushController(PushService pushService, HttpProperties httpProperties) {
		this.pushService = pushService;
		serverUrl = httpProperties.getPush().getUrl();
	}

	/**
	 * RestTemplate을 사용하여 PUSH SERVICE 서비스 가입 + USER 등록 API를 프록시 요청
	 * (해당 API는 통신 가능한 PUSH 서버가 구동중이어야 함)
	 * @param request 요청 헤더와 파라미터를 포함하는 Request 객체
	 * @return API 응답 데이터를 포함하는 성공 응답 ResponseEntity
	 */
	@PostMapping(value = "/api/push/user/regist")
	public ResponseEntity<Response<ResponseHeader, PushResult>> pushUserRegisterSample(
		Request<RequestHeader, RequestParameter> request) {

		// 프록시될 API 엔드포인트의 대상 URI 생성
		// - PUSH server서버를 기본 URL로 사용
		// - PUSH SERVICE 서비스 가입 + USER 등록 API를 목적지 경로로 추가
		// - 특수 문자를 적절하게 처리하기 위해 URI 구성 요소를 인코딩
		URI uri = UriComponentsBuilder
			.fromHttpUrl(serverUrl)
			.path("/new_rcv_register_service_and_user.ctl")
			.encode()
			.build()
			.toUri();

		// 푸시 서비스 및 사용자 등록 파라미터 설정
		MultiValueMap<String, Object> paramMap = pushService.getRegisterServiceAndUserParamMap();

		try {
			// pushService를 사용하여 프록시된 API 요청 처리
			// - 생성된 URI, 요청 본문, HTTP 메서드(이 경우 POST) 전달
			Map<String, Object> response = pushService.callApi(uri, paramMap, HttpMethod.POST);

			// 푸시 서비스 및 사용자 등록 결과 파싱
			PushResult result = pushService.parseRegisterServiceAndUserResult(response);

			// HTTP 상태 코드 200(OK)와 성공 응답 객체를 포함하는 ResponseEntity 반환
			return ResponseEntity.ok(ResponseFactory.createSuccessResponse(new ResponseHeader(), result).build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * RestTemplate을 사용하여 PUSH 발송 API 프록시 요청
	 * (해당 API는 통신 가능한 PUSH 서버가 구동중이어야 하고, pushUserRegisterSample API를 통해 등록된 사용자에게만 발송 가능)
	 * @param request 요청 헤더와 파라미터를 포함하는 Request 객체
	 * @return API 응답 데이터를 포함하는 성공 응답 ResponseEntity
	 */
	@PostMapping(value = "/api/push/send")
	public ResponseEntity<Response<ResponseHeader, PushSendResult>> pushSendSample(
		Request<RequestHeader, RequestParameter> request) {

		// 프록시될 API 엔드포인트의 대상 URI 생성
		// - PUSH server서버를 기본 URL로 사용
		// - 푸시 발송 API를 목적지 경로로 추가
		// - 특수 문자를 적절하게 처리하기 위해 URI 구성 요소를 인코딩
		URI uri = UriComponentsBuilder
			.fromHttpUrl(serverUrl)
			.path("/rcv_register_message.ctl")
			.encode()
			.build()
			.toUri();

		// 푸시 발송 파라미터 설정
		MultiValueMap<String, Object> paramMap = pushService.getSendPushParamMap();

		try {
			// pushService를 사용하여 프록시된 API 요청 처리
			// - 생성된 URI, 요청 본문, HTTP 메서드(이 경우 POST) 전달
			Map<String, Object> response = pushService.callApi(uri, paramMap, HttpMethod.POST);

			// 푸시 발송 결과 파싱
			PushSendResult result = pushService.parseSendResult(response);

			// HTTP 상태 코드 200(OK)와 성공 응답 객체를 포함하는 ResponseEntity 반환
			return ResponseEntity.ok(ResponseFactory.createSuccessResponse(new ResponseHeader(), result).build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}
