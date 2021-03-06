package com.jamesaq12wsx.gymtime;

import com.jamesaq12wsx.gymtime.configuration.AppProperties;
import com.jamesaq12wsx.gymtime.util.SpringContextHolder;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.OffsetDateTime;
import java.util.Optional;

@Api(hidden = true)
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableAsync
public class GymtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymtimeApplication.class, args);
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

	@Bean(name = "auditingDateTimeProvider")
	public DateTimeProvider dateTimeProvider() {
		return () -> Optional.of(OffsetDateTime.now());
	}

}
