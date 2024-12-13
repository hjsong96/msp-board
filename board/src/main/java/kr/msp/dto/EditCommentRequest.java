package kr.msp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCommentRequest extends UserRank {
	
	@NotNull
	private int boardNo;
	
	@NotNull
	private int commentNo;
	
	@Size(min = 2)
	@Size(max = 50)
	@NotNull
	private String commentContent;

}
