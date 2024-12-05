package kr.msp.example.http.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

import kr.msp.example.file.FileInfo;
import kr.msp.example.http.HttpProperties;
import kr.msp.example.http.service.HttpProxyDownloadService;
import kr.msp.example.http.service.HttpProxyLegacyService;
import kr.msp.example.http.service.HttpProxyService;
import kr.msp.example.http.dto.RequestParameter;
import kr.msp.example.http.dto.Result;
import kr.msp.example.http.tcp.TcpAliveManager;

import kr.morpheus.gateway.protocol.Request;
import kr.morpheus.gateway.protocol.RequestHeader;
import kr.morpheus.gateway.protocol.Response;
import kr.morpheus.gateway.protocol.ResponseHeader;
import kr.morpheus.gateway.response.ResponseFactory;

@Controller
@Slf4j
public class HttpProxyController {

	private final HttpProxyService httpProxyService;

	private final HttpProxyLegacyService httpProxyLegacyService;
	private final HttpProxyDownloadService httpProxyDownloadService;
	private static String serverUrl = "";

	public HttpProxyController(
		HttpProxyService httpProxyService, HttpProxyLegacyService httpProxyLegacyService,
		HttpProxyDownloadService httpProxyDownloadService,
		HttpProperties httpProperties) {
		this.httpProxyService = httpProxyService;
		this.httpProxyLegacyService = httpProxyLegacyService;
		this.httpProxyDownloadService = httpProxyDownloadService;
		serverUrl = httpProperties.getServer().getUrl();
	}

	/**
	 * RestTemplate을 사용하여 API 프록시 요청
	 * @param request 요청 헤더와 파라미터를 포함하는 Request 객체
	 * @return API 응답 데이터를 포함하는 성공 응답 ResponseEntity
	 * @throws JsonProcessingException 요청 본문을 JSON 형식으로 처리하는 데 문제가 발생할 경우
	 */
	@PostMapping(value = "/api/proxy/sample")
	public ResponseEntity<Response<ResponseHeader, Result>> restTemplateExample(
		Request<RequestHeader, RequestParameter> request) throws JsonProcessingException {

		// 프록시될 API 엔드포인트의 대상 URI 생성
		// - serverUrl을 기본 URL로 사용
		// - 목적지 경로 추가 (예시. 공지사항 조회 API)
		// - 특수 문자를 적절하게 처리하기 위해 URI 구성 요소를 인코딩
		URI uri = UriComponentsBuilder
			.fromHttpUrl(serverUrl)
			.path("/egov/api/noticeList.do")
			.encode()
			.build()
			.toUri();

		// httpProxyService를 사용하여 프록시된 API 요청 처리
		// - 생성된 URI, 요청 본문, HTTP 메서드(이 경우 GET) 전달
		Result result = httpProxyService.callApi(uri, request.getBody(), HttpMethod.GET);

		// HTTP 상태 코드 200(OK)와 성공 응답 객체를 포함하는 ResponseEntity 반환
		return ResponseEntity.ok(ResponseFactory.createSuccessResponse(new ResponseHeader(), result).build());

	}

	/**
	 * Legacy Host 정보 조회 후 연결
	 */
	@PostMapping(value = "/api/http/legacy/proxy")
	public ResponseEntity<Response<ResponseHeader, Result>> httpLegacyProxySample(
		Request<RequestHeader, MultiValueMap<String, Object>> request) throws Exception {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//현재 사용가능한 네거시 호스트 리스트
		List<String> aliveHostList = TcpAliveManager.getInstance().getAliveHostNameList();
		log.info("## Alive Host List : {}", gson.toJson(aliveHostList));

		//오류로 등록된 호스트 리스트 정보
		List<String> badHostNameList = TcpAliveManager.getInstance().getBadHostNameList();
		log.info("## Bad Host Name List : {}", gson.toJson(badHostNameList));

		//연결할 Legacy Host 정보
		String connectHost = TcpAliveManager.getInstance().getConnectableHostName();
		log.info("## Connectable Host Name : {}", connectHost);

		// 프록시될 API 엔드포인트의 대상 URI 생성
		// - 연결할 Legacy Host 정보를 기본 URL로 사용
		// - 목적지 경로 추가
		// - 특수 문자를 적절하게 처리하기 위해 URI 구성 요소를 인코딩
		URI uri = UriComponentsBuilder
			.fromHttpUrl(connectHost)
			.path("/index.html")
			.encode()
			.build()
			.toUri();

		// httpProxyService를 사용하여 프록시된 API POST 요청
		// - 생성된 URI, 요청 본문, HTTP 메서드(이 경우 POST) 전달
		Result resultBean = httpProxyLegacyService.callApi(uri, request.getBody(), HttpMethod.POST);

		// HTTP 상태 코드 200(OK)와 성공 응답 객체를 포함하는 ResponseEntity 반환
		return ResponseEntity.ok(ResponseFactory.createSuccessResponse(new ResponseHeader(), resultBean).build());

	}

	/**
	 * Legacy System 파일 다운로드 API
	 * @param fileExtension 파일 확장자 (예: "pdf", "zip")
	 * @param fileId 다운로드 할 파일의 확장자 제외한 파일명
	 * @return 다운로드된 파일 정보를 포함하는 성공 응답 ResponseEntity
	 * @throws IOException 다운로드 또는 파일 처리 중 문제 발생 시
	 */
	@GetMapping(value = "/api/proxy/download/{fileExtension}/{fileId}")
	public ResponseEntity<Response<ResponseHeader, FileInfo>> proxyFileDownloadSample(
		@PathVariable String fileExtension, @PathVariable String fileId) throws IOException {

		//httpProxyDownloadService를 사용하여 파일 다운로드 수행
		// - 파일 확장자와 파일 식별자를 매개변수로 전달
		FileInfo fileInfo = httpProxyDownloadService.download(fileExtension, fileId);
		// ResponseFactory를 사용하여 성공 응답 객체 생성
		// - 빈 ResponseHeader와 다운로드된 파일 정보(FileInfo)를 포함
		// - HTTP 상태 코드 200(OK)와 성공 응답 객체를 포함하는 ResponseEntity 반환
		return ResponseEntity.ok(ResponseFactory.createSuccessResponse(new ResponseHeader(), fileInfo).build());

	}

	// /**
	//  * Legacy System 파일 업로드 API
	//  * @param multipartFile 업로드할 파일을 담고있는 MultipartFile 객체
	//  */
	// @PostMapping(value = "/api/proxy/upload")
	// public void proxyFileUploadSample(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
	//
	// }

}
