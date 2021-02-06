package pe.com.dackng.example.shared;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("mqtt")
@Getter
@Setter
public class YamlConfig {
	
	private String hostname;
	private Bindings bindings;
	
	public YamlConfig() {
		bindings = new Bindings();
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public class Bindings{
		private String humidity;
		private String rain;
		private String openValve;
		private String openedValve;
		private String closeValve;
		private String closedValve;
	}
}
