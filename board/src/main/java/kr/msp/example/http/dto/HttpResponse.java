package kr.msp.example.http.dto;

import org.springframework.http.HttpHeaders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponse {
	private HttpHeaders header;		//헤더 정보
	private String body;		//반환받은 실제 데이터 정보
	private int statusCode;	//HTTP Status Code
}
