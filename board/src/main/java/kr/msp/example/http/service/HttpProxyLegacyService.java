package kr.msp.example.http.service;

import java.net.URI;
import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.msp.example.http.dto.Result;

@Service
public class HttpProxyLegacyService {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Gson gson = new GsonBuilder()
		.setPrettyPrinting()
		.serializeNulls()
		.create();

	/**
	 * RestTemplate을 사용하여 외부 API 통신하는 샘플 메서드
	 * @param uri 호출할 uri
	 * @param multiValueMap 호출 시 넘길 MultiValueMap 파라미터
	 * @param httpMethod HTTP METHOD 방식
	 * @return 호출 결과가 담겨진 {@link Result} DTO 객체
	 * @throws JsonProcessingException jackson 라이브러리를 사용하여 호출 결과를 파싱할 경우 발생
	 */
	public Result callApi(URI uri, MultiValueMap<String, Object> multiValueMap, HttpMethod httpMethod) throws
		JsonProcessingException {

		//---------------------------------------------------
		// 1. HttpHeaders 객체 선언
		//---------------------------------------------------
		HttpHeaders headers = new HttpHeaders();

		//---------------------------------------------------
		// 2. 파라미터와 HttpHeaders를 HttpEntity 객체에 설정
		//---------------------------------------------------
		HttpEntity<MultiValueMap<String, Object>> multiValueMapHttpEntity = new HttpEntity<>(multiValueMap, headers);

		RestTemplate restTemplate = new RestTemplateBuilder()
			.setConnectTimeout(Duration.ofSeconds(5))
			.setReadTimeout(Duration.ofSeconds(5))
			.build();

		//---------------------------------------------------
		// 3. API 호출
		//---------------------------------------------------
		ResponseEntity<String> response = restTemplate.exchange(
			uri,                            //요청할 서버주소
			httpMethod,                     //요청 방식
			multiValueMapHttpEntity,        //파라미터
			String.class);                  //요청 시 반한될 데이터 타입

		//---------------------------------------------------
		// 4. API 호출 결과 파싱
		//---------------------------------------------------
		String postResponseBody = response.getBody();

		//방법 1) gson 라이브러리 사용하여 DTO 매핑
		Result parseWithGsonResult = parseWithGsonResult = gson.fromJson(postResponseBody, Result.class);

		//방법 2) jackson 라이브러리 사용하여 DTO 매핑
		Result parseWithGsonResultJackson = objectMapper.readValue(postResponseBody, Result.class);

		return parseWithGsonResult;
	}
}
