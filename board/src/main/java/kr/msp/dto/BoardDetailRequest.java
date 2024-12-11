package kr.msp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDetailRequest {
	
	@NotNull
	private int boardNo;
	
	@Min(value = 1)
    private int page = 1;          
	
    @Min(value = 5)
    private int size = 5;           
}
