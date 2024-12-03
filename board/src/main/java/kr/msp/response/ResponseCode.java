package kr.msp.response;

import java.util.Arrays;

public enum ResponseCode {
	
	OK(200),
	Created(201),
	NoContent(204),
	BadRequest(400),
	Unauthorized(401),
	AccessDenied(403),
	ForceChangePassword(402),
	NotFound(404),
	Conflict(409),
	UnprocessableRequest(422),
	ServerError(500),
	Unknown(999);

	private int result_code;

	ResponseCode(int result_code) {
		this.result_code = result_code;
	}
	
	public static ResponseCode of(int result_code) {
		return Arrays.stream(ResponseCode.values()).filter(v -> v.value() == result_code)
			.findFirst().orElseGet(() -> ResponseCode.Unknown);
	}

	public int value() {
		return this.result_code;
	}


}
