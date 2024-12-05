package kr.msp.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
	private int userNo;
	@NotNull(message = "아이디는 필수 항목입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 알파벳과 숫자만 포함할 수 있습니다.")
	private String userID;
	@NotNull(message = "비밀번호는 필수 항목입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "비밀번호는 알파벳과 숫자만 포함할 수 있습니다.")
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