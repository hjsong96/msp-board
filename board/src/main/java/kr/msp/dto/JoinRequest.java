package kr.msp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 6)
	private String userID;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 100)
	private String userPW;
	
	@NotNull
	@Pattern(regexp = "^[가-힣]+$")
	@Size(max = 3)
	private String userName;

}
