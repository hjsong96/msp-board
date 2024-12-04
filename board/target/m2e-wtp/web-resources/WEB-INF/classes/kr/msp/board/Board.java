package kr.msp.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {
    private int boardNo;
    private int boardType;
    private String boardTitle;
    private int commentCount;
    private String boardCreateDate;
    private String boardUpdateDate;
    private String userID;
    private String userName;
}
