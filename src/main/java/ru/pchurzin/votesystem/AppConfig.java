package ru.pchurzin.votesystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.pchurzin.votesystem.repository.RepositoryConfig;
import ru.pchurzin.votesystem.security.SecurityConfig;
import ru.pchurzin.votesystem.service.ServiceConfig;
import ru.pchurzin.votesystem.web.WebConfig;

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class, WebConfig.class, SecurityConfig.class})
public class AppConfig {
}
