package kr.msp.example.http.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.msp.example.file.FileInfo;
import kr.msp.example.http.HttpProperties;

import java.nio.file.attribute.BasicFileAttributes;

@Service
public class HttpProxyDownloadService {

	HttpProperties.Legacy legacy;

	public HttpProxyDownloadService(HttpProperties httpProperties) {
		this.legacy = httpProperties.getLegacy();
	}

	/**
	 * 파일 다운로드 후 정보를 리턴하는 메서드
	 * @param fileExtension 파일 확장자 (예: "png", "jpg")
	 * @param fileId 다운로드할 파일 식별자
	 * @return 다운로드된 파일 정보를 담은 FileInfo 객체
	 * @throws IOException 다운로드 또는 파일 처리 중 문제 발생 시
	 */
	public FileInfo download(String fileExtension, String fileId) throws IOException {
		// 디렉토리 경로 조작 방지 필터 적용
		fileExtension = toWebFileFilter(fileExtension);
		fileId = toWebFileFilter(fileId);

		// 다운로드 할 Legacy System 주소 정보
		//예시 ) http://localhost:28080/msp-gw/api/file/download/ + png(파일 확장자) + "/" + buzzLight(확장자 제외한 파일명)
		legacy.setUrl(legacy.getUrl() + fileExtension + "/" + fileId);

		// RestTemplate 객체 생성 (HTTP 요청용)
		RestTemplate restTemplate = new RestTemplate();
		// 헤더 설정 (빈 헤더 사용)
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		// 파일 내용을 바이트 배열로 다운로드
		ResponseEntity<byte[]> result = restTemplate.exchange(legacy.getUrl(), HttpMethod.GET, httpEntity,
			byte[].class);
		// 다운로드 파일 저장 경로 설정
		String destination = legacy.getDownload() + result.getHeaders().getContentDisposition().getFilename();
		// 다운로드된 바이트 배열을 파일로 저장
		Path path = Paths.get(destination);
		Files.write(path, Objects.requireNonNull(result.getBody()));
		// 다운로드된 파일 정보 확인
		BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);

		// 파일 존재 여부 확인 (파일 다운로드 성공 여부)
		if(Files.exists(path)){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setLocation(destination);
			fileInfo.setSize(fileAttributes.size());
			return fileInfo;
		}else{
			throw new IOException("파일을 다운로드 할 수 없습니다.");
		}

	}

	/**
	 * 웹 파일명 필터링 메소드
	 *
	 * @param plainText 변환할 파일명(문자열)
	 * @return 웹 상에서 사용하기에 적합한 파일명(문자열)
	 *
	 * <ul>
	 *  <li>빈 문자열("")이 입력된 경우 그대로 반환</li>
	 *  <li>파일 경로 구분자 "/" 를 제거</li>
	 *  <li>중복된 파일 경로 구분자 "//" 를 제거</li>
	 *  <li>특수 문자 "." 을 제거</li>
	 *  <li>특수 문자 "&" 을 제거</li>
	 * </ul>
	 */
	private String toWebFileFilter(String plainText) {
		if (!plainText.isEmpty()) {
			plainText = plainText.replace("/", "");
			plainText = plainText.replace("//", "");
			plainText = plainText.replace(".", "");
			plainText = plainText.replace("&", "");
		}
		return plainText;
	}

}
