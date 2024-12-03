package kr.msp.example.http.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushResult {

	private String DEVICEID_CHANGE_YN;

	private String APPID;

	private String AUTHKEY;

	private String PSID;

}
