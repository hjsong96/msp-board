package kr.msp.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCommentRequest extends UserRank {
	
	@NotNull
	private int boardNo;
	
	@NotNull
	private int commentNo;

}
