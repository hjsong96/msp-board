package kr.msp.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseHeader {

	private String result_code;
	private String result_msg;
	
	public ResponseHeader() {
	}
	
    public ResponseHeader(ResponseCode responseCode) {
        this.result_code = responseCode.getCode(); 
        this.result_msg = responseCode.getMessage(); 
    }

	public ResponseHeader(String result_code, String result_msg) {
		this.result_code = result_code;
		this.result_msg = result_msg;
	}
}
