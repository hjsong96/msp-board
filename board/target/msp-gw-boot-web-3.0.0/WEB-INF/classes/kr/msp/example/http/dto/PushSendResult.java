package kr.msp.example.http.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushSendResult {

	private String SENDMSG_SEQNO;

	private String REG_SUCCESS_CNT;

	private String REG_FAIL_CNT;

	private List<String> NOTEXISTCUIDS;

	private String UPNS;

	private String APNS;

	private String FCM;

	private String WPNS;

	private String CUST_VAR1;

	private String CUST_VAR2;

	private String CUST_VAR3;

	private String HOST_URL;

	private String CUST_KEY;

}
