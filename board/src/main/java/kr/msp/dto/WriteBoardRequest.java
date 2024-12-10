package kr.msp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteBoardRequest {
	
	@NotNull
    @Min(value = 0)
    @Max(value = 1)
    private int boardType;
    
	@NotNull
	@Size(min = 2)
	@Size(max = 20)
    private String boardTitle;
	
	@NotNull
	@Size(min = 2)
	@Size(max = 100)
    private String boardContent;

	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Size(max = 6)
	private String userID;
	
}