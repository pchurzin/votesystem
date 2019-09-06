package ru.pchurzin.votesystem.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Import(RepositoryConfig.class)
@Configuration
public class RepositoryTestConfig {
    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:hsqldb:mem:/votesystem-test", "sa", "");
    }
}
