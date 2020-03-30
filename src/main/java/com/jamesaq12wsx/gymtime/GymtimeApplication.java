package com.jamesaq12wsx.gymtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class GymtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymtimeApplication.class, args);
	}

}
