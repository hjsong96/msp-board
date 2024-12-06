package kr.msp.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {
    private int boardNo;
    private String userID;
    private int commentNo;
    private int boardType;
    private String boardTitle;
    private String boardContent;
    private String boardCreateDate;
    private String boardUpdateDate;
    private int commentCount;
    private String boardDelYn;
}
