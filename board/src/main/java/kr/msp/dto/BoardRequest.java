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
public class BoardRequest {
	
	@Min(value = 1, message = "페이지 번호는 1 이상의 값이어야 합니다.")
    private int page = 1;          
    @Min(value = 10, message = "페이지 크기는 10 이상이어야 합니다.")
    private int size = 10;           
    @Min(value = 0, message = "검색 타입은 0 이상이어야 합니다.")
    @Max(value = 2, message = "검색 타입은 0, 1, 2 중 하나여야 합니다.")
    private int searchType = 0; 
    private String searchKeyword = ""; 

}
