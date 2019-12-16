package com.metro.mcrm.app.mcrmApp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean(name = "pp12")
    @Autowired
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource dataSourcepp12() {
    	

        return  DataSourceBuilder.create().build();
    }

    @Bean(name = "pp21")
    @Primary
    @Autowired
    @ConfigurationProperties(prefix="spring.seconddatasource")
    public javax.sql.DataSource dataSourcepp21() {
        return  DataSourceBuilder.create().build();
    }
    
    @Bean(name = "mesconpp")
    @Autowired
    @ConfigurationProperties(prefix="spring.fifthdatasource")
    public javax.sql.DataSource dataSourcemesconpp() {
        return  DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateOne")
    @Autowired
    public JdbcTemplate jdbcTemplateOne(@Qualifier("pp12")DataSource pp12) {
        return new JdbcTemplate((javax.sql.DataSource) pp12);
    }

    @Bean(name = "jdbcTemplateTwo")
    @Autowired
    public JdbcTemplate jdbcTemplateTwo(@Qualifier("pp21") DataSource pp21) {
        return new JdbcTemplate((javax.sql.DataSource) pp21);
    }
    
    @Bean(name = "jdbcTemplateFifth")
    @Autowired
    public JdbcTemplate jdbcTemplateFifth(@Qualifier("mesconpp") DataSource mesconpp) {
        return new JdbcTemplate((javax.sql.DataSource) mesconpp);
    }
}