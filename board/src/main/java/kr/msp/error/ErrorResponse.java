package kr.msp.error;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse{
	private int result_code;
	private String result_msg;
}
