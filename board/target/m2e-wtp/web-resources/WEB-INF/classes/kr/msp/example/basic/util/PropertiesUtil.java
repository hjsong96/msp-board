package kr.msp.example.basic.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesUtil{

	private final Environment environment;

	public PropertiesUtil(Environment environment){
		this.environment = environment;
	}

	public String getPropertyName(String propertyName) {
		return environment.getProperty(propertyName);
	}
}
