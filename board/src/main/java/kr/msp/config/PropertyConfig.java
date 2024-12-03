package kr.msp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import kr.msp.example.file.FileProperties;
import kr.msp.example.http.HttpProperties;

@Configuration
@EnableConfigurationProperties({FileProperties.class, HttpProperties.class})
public class PropertyConfig {
}
