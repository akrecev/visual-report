package com.rokdc.report.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})
public class DataSourceConfig {
    //@Primary
    @Bean(name = "sqliteDS")
    @ConfigurationProperties(prefix = "app.datasource.sqlite")
    public DataSource sqliteDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "oracleDS")
    @ConfigurationProperties(prefix = "app.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .driverClassName("oracle.jdbc.OracleDriver").build();
    }


    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
