package kr.msp.example.encode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Encode {
	private String encURL;
	private String encName;
	private String encPass;

	public Encode(String encURL, String encName, String encPass){
		this.encURL = encURL;
		this.encName = encName;
		this.encPass = encPass;
	}
}
