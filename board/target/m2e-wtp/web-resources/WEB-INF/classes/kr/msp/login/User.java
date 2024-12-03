package kr.msp.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

	private int userNo;
	private String userID;
	private String userPW;
	private String userName;
	private int userRank;
}

/*
 * 유저1 : {
        "userPW": "0614",
        "userID": "U00449",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVMDA0NDkiLCJpYXQiOjE3MzMxODkwMDIsImV4cCI6MTczMzE5MjYwMn0.RhDgscj4TQbP0oOFqSDMNi0nqIM-WTiFcbaxCJr5wO0"
    }
 */