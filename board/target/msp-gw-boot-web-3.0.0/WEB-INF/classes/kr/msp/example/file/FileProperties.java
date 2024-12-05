package kr.msp.example.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {

	private String location;


}
