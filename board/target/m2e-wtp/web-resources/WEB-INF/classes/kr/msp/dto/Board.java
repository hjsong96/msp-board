package kr.msp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {
    private int boardNo;
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 알파벳과 숫자만 포함할 수 있습니다.")
	@Size(max = 6, message = "아이디는 최대 4글자입니다.")
    private String userID;
    private int commentNo;
    @Min(value = 0, message = "보드 타입은 0 이상이어야 합니다.")
    @Max(value = 1, message = "보드 타입은 0, 1 중 하나여야 합니다.")
    private int boardType;
	@Size(min = 2, message = "게시물 제목은 최소 2글자입니다.")
	@Size(max = 20, message = "게시물 제목은 최대 20글자입니다.")
    private String boardTitle;
	@Size(min = 2, message = "게시물 내용은 최소 2글자입니다.")
	@Size(max = 100, message = "게시물 내용은 최대 100글자입니다.")
    private String boardContent;
    private String boardCreateDate;
    private String boardUpdateDate;
    private int commentCount;
    private String boardDelYn;
}
