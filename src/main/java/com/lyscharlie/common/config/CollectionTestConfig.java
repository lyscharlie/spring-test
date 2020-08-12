package com.lyscharlie.common.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "my.test")
@Data
public class CollectionTestConfig {

	private List<String> list;

	private Map<String, String> map;
	
}
