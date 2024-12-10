package kr.msp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class LoginRequest {

	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 6)
	private String userID;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 100)
	private String userPW;
}
