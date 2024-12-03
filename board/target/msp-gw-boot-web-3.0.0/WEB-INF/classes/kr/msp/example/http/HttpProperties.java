package kr.msp.example.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "http")
public class HttpProperties {

	@NestedConfigurationProperty
	private Server server = new Server();

	@NestedConfigurationProperty
	private Legacy legacy = new Legacy();

	@NestedConfigurationProperty
	private Push push = new Push();


	@Getter
	@Setter
	public static class Server {
		private String url;
	}

	@Getter
	@Setter
	public static class Legacy {
		private String url;
		private String download;
	}

	@Getter
	@Setter
	public static class Push {
		private String url;
	}

}
