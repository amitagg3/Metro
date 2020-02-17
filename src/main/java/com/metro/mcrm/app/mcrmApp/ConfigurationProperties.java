package com.metro.mcrm.app.mcrmApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class ConfigurationProperties {
	@Bean
	@Autowired
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
	    properties.setLocation(new FileSystemResource("/T:/Kunde/04_CRM/mcrmApp/properties/conf.properties"));
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
	}
}
