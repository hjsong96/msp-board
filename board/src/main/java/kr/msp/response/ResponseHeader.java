package kr.msp.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseHeader {

	private int result_code;
	private String result_msg;
	
	public ResponseHeader() {
	}

	public ResponseHeader(ResponseCode result_code, String result_msg) {
		this.result_code = result_code.value();
		this.result_msg = result_msg;
	}
}
