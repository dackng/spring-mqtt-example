package pe.com.dackng.example.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import lombok.AllArgsConstructor;

@Configuration
@EnableMongoAuditing
@AllArgsConstructor
public class AuditingConfig implements AuditorAware<String> {
	
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("dackng");
	}
}
