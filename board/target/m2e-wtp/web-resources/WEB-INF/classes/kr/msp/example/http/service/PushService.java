package kr.msp.example.http.service;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;

import kr.msp.example.http.dto.PushResult;
import kr.msp.example.http.dto.PushSendResult;

@Slf4j
@Service
public class PushService {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Gson gson = new GsonBuilder()
		.setPrettyPrinting()
		.serializeNulls()
		.create();

	public Map<String, Object> callApi(URI uri, MultiValueMap<String, Object> parameter, HttpMethod httpMethod) throws JsonProcessingException {
		//---------------------------------------------------
		// 1. API 통신 시 필요한 파라미터 설정
		//---------------------------------------------------
		//파라미터 타입 1) 단순 String 파라미터
		// String paramString = parameter.getParameter();
		//파라미터 타입 2) key, value 형식 파라미터
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		paramMap.addAll(parameter);

		//---------------------------------------------------
		// 2. HttpHeaders 객체 선언
		//---------------------------------------------------
		HttpHeaders headers = new HttpHeaders();
		//headers 옵션 설정
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//---------------------------------------------------
		// 3. 세팅한 파라미터와 HttpHeaders를 HttpEntity 객체에 설정
		//---------------------------------------------------
		//파라미터 타입 1) 단순 String 파라미터
		// HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramString, headers);
		//파라미터 타입 2) key, value 형식 파라미터
		HttpEntity<MultiValueMap<String, Object>> multiValueMapHttpEntity = new HttpEntity<>(paramMap, headers);

		RestTemplate restTemplate = new RestTemplate();

		//---------------------------------------------------
		// 4. API 호출
		//---------------------------------------------------
		ResponseEntity<Map> response = restTemplate.exchange(
			uri,                            //요청할 서버주소
			httpMethod,                     //요청 방식
			multiValueMapHttpEntity,            //파라미터
			Map.class);                  //요청 시 반한될 데이터 타입

		//---------------------------------------------------
		// Optional. HTTP 통신 응답값 DTO 세팅
		//---------------------------------------------------
		// HttpResponse httpResponse = new HttpResponse();
		// httpResponse.setHeader(postResponse.getHeaders());
		// httpResponse.setBody(postResponse.getBody());
		// httpResponse.setStatusCode(postResponse.getStatusCodeValue());

		return (Map<String, Object>) response.getBody();
	}

	public PushSendResult parseSendResult(Map<String, Object> postResponseBody) throws Exception {
		//---------------------------------------------------
		// API 호출 결과 파싱
		//---------------------------------------------------
		Map<String, String> headMap = (Map<String, String>) postResponseBody.get("HEADER");
		if (!headMap.get("RESULT_CODE").equals("0000")) {
			throw new Exception("푸시 발송 실패");
		}
		Map<String, String> bodyMap = (Map<String, String>) postResponseBody.get("BODY");

		//방법 1) gson 라이브러리 사용하여 DTO 매핑
		JsonElement jsonElement = gson.toJsonTree(bodyMap);
		PushSendResult pushResult = gson.fromJson(jsonElement, PushSendResult.class);

		//방법 2) jackson 라이브러리 사용하여 DTO 매핑
		// PushSendResult parseWithGsonResultJackson = objectMapper.readValue(resultMap, PushSendResult.class);

		return pushResult;
	}

	public MultiValueMap<String, Object> getSendPushParamMap() {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		paramMap.add("TYPE", "E");
		paramMap.add("CUID", "MSPTEST001");
		//paramMap.add("CUID", "[\"testuser1\",\"testuser2\",\"testuser3\"]");  //여러명 보낼 경우
		paramMap.add("MESSAGE", "{\"title\":\"안녕하세요.유라클 공지사항입니다.\",\"body\":\"오늘 새벽에 정기점검 있습니다.\n 감사합니다.\"}");
		paramMap.add("TEMPLATE_YN", "N");  //보내는 메세지의 치환변수(%CUID% or %CNAME%)가 있을 경우 Y로 보냄.
		paramMap.add("PRIORITY", "3");
		paramMap.add("BADGENO", "0");
		paramMap.add("RESERVEDATE", "");  //예약발송일 경우 ex)20180708 153000
		paramMap.add("SERVICECODE", "ALL");   // 발송 서비스코드 ALL, ALL2, PUBLIC, PRIVATE 중 택일
		paramMap.add("EXT", "");
		paramMap.add("SENDERCODE", "admin");
		paramMap.add("APP_ID", "com.uracle.push.demo");
		paramMap.add("DB_IN", "Y");
		paramMap.add("SPLIT_MSG_CNT", "0");
		paramMap.add("DELAY_SECOND", "0");
		paramMap.add("PUSH_FAIL_SMS_SEND", "N");

		return paramMap;
	}

	public PushResult parseRegisterServiceAndUserResult(Map<String, Object> postResponseBody) throws Exception {
		//---------------------------------------------------
		// API 호출 결과 파싱
		//---------------------------------------------------
		Map<String, String> headMap = (Map<String, String>) postResponseBody.get("HEADER");
		if (!headMap.get("RESULT_CODE").equals("0000")) {
			throw new Exception("푸시 발송 실패");
		}

		List<Map<String, Object>> bodyList = (List) postResponseBody.get("BODY");
		Map<String, Object> resultMap = bodyList.get(0);

		//방법 1) gson 라이브러리 사용하여 DTO 매핑
		JsonElement jsonElement = gson.toJsonTree(resultMap);
		PushResult pushResult = gson.fromJson(jsonElement, PushResult.class);

		//방법 2) jackson 라이브러리 사용하여 DTO 매핑
		// PushResult parseWithGsonResultJackson = objectMapper.readValue(resultMap, PushResult.class);

		return pushResult;
	}

	public MultiValueMap<String, Object> getRegisterServiceAndUserParamMap() {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		paramMap.add("APP_ID", "com.uracle.push.demo");
		paramMap.add("CUID", "MSPTEST001");
		paramMap.add("CNAME", "MSPTEST001");
		paramMap.add("DEVICE_ID", "c2be3587-700b-3f72-9b66-e02e7e191e40");
		paramMap.add("DEVICE_TYPE", "A");
		paramMap.add("PNSID", "FCM");
		paramMap.add("PSID", "6b4bbafba54c5bd3c804d2bccac9efb6335f453f");

		return paramMap;
	}
}
