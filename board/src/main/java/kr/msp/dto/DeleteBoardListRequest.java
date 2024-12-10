package kr.msp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class DeleteBoardListRequest extends UserRank {
	
	@NotNull
	@Size(min = 1)
	private List<Integer> boardNos; 

}
