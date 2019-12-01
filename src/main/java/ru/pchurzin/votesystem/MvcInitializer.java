package ru.pchurzin.votesystem;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.pchurzin.votesystem.repository.RepositoryConfig;
import ru.pchurzin.votesystem.security.SecurityConfig;
import ru.pchurzin.votesystem.service.ServiceConfig;
import ru.pchurzin.votesystem.web.WebConfig;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
