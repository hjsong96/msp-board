package kr.msp.dto;

import javax.validation.constraints.NotNull;

public class DeleteBoardRequest extends UserRank {
	
	@NotNull
	private int boardNo;

}
