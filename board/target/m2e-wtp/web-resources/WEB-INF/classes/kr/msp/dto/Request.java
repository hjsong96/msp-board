package kr.msp.dto;

import kr.msp.response.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
	
	private String result_code;
	private String result_msg;
	
	public Request() {
	}
	
    public Request(ResponseCode responseCode) {
        this.result_code = responseCode.getCode(); 
        this.result_msg = responseCode.getMessage(); 
    }

	public Request(String result_code, String result_msg) {
		this.result_code = result_code;
		this.result_msg = result_msg;
	}
}
