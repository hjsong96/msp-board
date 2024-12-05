package kr.msp.example.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
	private String location;
	private String ext;
	private long size;

}
