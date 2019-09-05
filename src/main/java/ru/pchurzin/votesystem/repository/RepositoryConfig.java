package ru.pchurzin.votesystem.repository;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@ComponentScan
@PropertySource("classpath:db/db.properties")
@Configuration
public class RepositoryConfig {

    private final Environment env;

    @Autowired
    public RepositoryConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setDriverClassName(env.getProperty("db.driverClass"));
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:db/changelog.sql");
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

}
