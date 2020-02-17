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
	

    @Bean(name = "E1")
    @Autowired
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource dataSourceE1() {
    	

        return  DataSourceBuilder.create().build();
    }

    @Bean(name = "E2")
    @Primary
    @Autowired
    @ConfigurationProperties(prefix="spring.seconddatasource")
    public javax.sql.DataSource dataSourceE2() {
        return  DataSourceBuilder.create().build();
    }
    @Bean(name = "E3")
    @Autowired
    @ConfigurationProperties(prefix="spring.thirddatasource")
    public javax.sql.DataSource dataSourceE3() {
        return  DataSourceBuilder.create().build();
    }
    @Bean(name = "E0")
    @Autowired
    @ConfigurationProperties(prefix="spring.fourthdatasource")
    public javax.sql.DataSource dataSourceE0() {
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
    public JdbcTemplate jdbcTemplateOne(@Qualifier("E1")DataSource E1) {
        return new JdbcTemplate((javax.sql.DataSource) E1);
    }

    @Bean(name = "jdbcTemplateTwo")
    @Autowired
    public JdbcTemplate jdbcTemplateTwo(@Qualifier("E2") DataSource E2) {
        return new JdbcTemplate((javax.sql.DataSource) E2);
    }
    @Bean(name = "jdbcTemplateThree")
    @Autowired
    public JdbcTemplate jdbcTemplateThree(@Qualifier("E3") DataSource E3) {
        return new JdbcTemplate((javax.sql.DataSource) E3);
    }
    @Bean(name = "jdbcTemplateFour")
    @Autowired
    public JdbcTemplate jdbcTemplateFour(@Qualifier("E0") DataSource E0) {
        return new JdbcTemplate((javax.sql.DataSource) E0);
    }
    
    
    @Bean(name = "jdbcTemplateFifth")
    @Autowired
    public JdbcTemplate jdbcTemplateFifth(@Qualifier("mesconpp") DataSource mesconpp) {
        return new JdbcTemplate((javax.sql.DataSource) mesconpp);
    }
}