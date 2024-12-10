package kr.msp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CheckIDRequest {
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 6)
	private String userID;

}
