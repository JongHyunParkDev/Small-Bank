package com.dev.was.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;


@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "data")
public class DataConfig {
    private Map<String, String> routedBus;
}
