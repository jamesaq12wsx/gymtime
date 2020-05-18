package com.jamesaq12wsx.gymtime.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.filepath")
@Getter
@Setter
public class UploadFIlePathConfig {

    private String image;

}
