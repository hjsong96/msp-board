package kr.msp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardListRequest {
	
	@Min(value = 1)
    private int page = 1;          
	
    @Min(value = 10)
    private int size = 10;           
    
    @Min(value = 0)
    @Max(value = 2)
    private int searchType = 0; 
    
    @Size(max = 20)
    private String searchKeyword = ""; 

}
