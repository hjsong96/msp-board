package kr.msp.example.http.service;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

import kr.msp.example.http.dto.RequestParameter;
import kr.msp.example.http.dto.Result;

@Slf4j
@Service
public class HttpProxyService {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Gson gson = new GsonBuilder()
		.setPrettyPrinting()
		.serializeNulls()
		.create();

	/**
	 * RestTemplate을 사용하여 외부 API 통신하는 샘플 메서드
	 * @param uri 호출할 uri
	 * @param parameter 호출 시 넘길 {@link RequestParameter} DTO 객체
	 * @param httpMethod HTTP METHOD 방식
	 * @return 호출 결과가 담겨진 {@link Result} DTO 객체
	 * @throws JsonProcessingException jackson 라이브러리를 사용하여 호출 결과를 파싱할 경우 발생
	 */
	public Result callApi(URI uri, RequestParameter parameter, HttpMethod httpMethod) throws JsonProcessingException {
		//---------------------------------------------------
		// 1. API 통신 시 필요한 파라미터 설정
		//---------------------------------------------------
		//파라미터 타입 1) 단순 String 파라미터
		String paramString = parameter.getParameter();
		//파라미터 타입 2) key, value 형식 파라미터
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		paramMap.add("title", "life");
		paramMap.add("author", "Jane");

		//---------------------------------------------------
		// 2. HttpHeaders 객체 선언
		//---------------------------------------------------
		HttpHeaders headers = new HttpHeaders();
		//headers 옵션 설정
		//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//---------------------------------------------------
		// 3. 세팅한 파라미터와 HttpHeaders를 HttpEntity 객체에 설정
		//---------------------------------------------------
		//파라미터 타입 1) 단순 String 파라미터
		HttpEntity<String> stringHttpEntity = new HttpEntity<>("BBSMSTR_AAAAAAAAAAAA", headers);
		//파라미터 타입 2) key, value 형식 파라미터
		// HttpEntity<MultiValueMap<String, Object>> multiValueMapHttpEntity = new HttpEntity<>(paramMap, headers);

		RestTemplate restTemplate = new RestTemplate();

		//---------------------------------------------------
		// 4. API 호출
		//---------------------------------------------------
		ResponseEntity<String> response = restTemplate.exchange(
			uri,                            //요청할 서버주소
			httpMethod,                     //요청 방식
			stringHttpEntity,            	//파라미터
			String.class);                  //요청 시 반한될 데이터 타입

		//---------------------------------------------------
		// Optional. HTTP 통신 응답값 DTO 세팅
		//---------------------------------------------------
		// HttpResponse httpResponse = new HttpResponse();
		// httpResponse.setHeader(postResponse.getHeaders());
		// httpResponse.setBody(postResponse.getBody());
		// httpResponse.setStatusCode(postResponse.getStatusCodeValue());

		//---------------------------------------------------
		// 5. API 호출 결과 파싱
		//---------------------------------------------------
		String postResponseBody = response.getBody();

		//방법 1) gson 라이브러리 사용하여 API 호출 결과 필드 DTO 매핑
		Result parseWithGsonResult = gson.fromJson(postResponseBody, Result.class);

		//방법 2) jackson 라이브러리 사용하여 API 호출 결과 필드 DTO 매핑
		Result parseWithGsonResultJackson = objectMapper.readValue(postResponseBody, Result.class);

		return parseWithGsonResult;
	}

}
