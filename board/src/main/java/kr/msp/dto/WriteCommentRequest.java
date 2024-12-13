package kr.msp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteCommentRequest {
	
	@NotNull
	private int boardNo;
	
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 6)
	private String userID;
	
	@Size(min = 2)
	@Size(max = 50)
	@NotNull
	private String commentContent;
	

}
