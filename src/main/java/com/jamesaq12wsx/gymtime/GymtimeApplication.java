package com.jamesaq12wsx.gymtime;

import com.jamesaq12wsx.gymtime.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class GymtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymtimeApplication.class, args);
	}

}
