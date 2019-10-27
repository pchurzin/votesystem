package ru.pchurzin.votesystem;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.pchurzin.votesystem.repository.RepositoryConfig;
import ru.pchurzin.votesystem.security.SecurityConfig;
import ru.pchurzin.votesystem.service.ServiceConfig;
import ru.pchurzin.votesystem.web.WebConfig;

public class Main {

    private static final String CONTEXT_PATH = "/";
    private static final String PATH_SPEC = "/";
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {

        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.register(WebConfig.class, RepositoryConfig.class, ServiceConfig.class, SecurityConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(wac);
        ServletHolder servletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(CONTEXT_PATH);
        servletContextHandler.addServlet(servletHolder, PATH_SPEC);
        servletContextHandler.addEventListener(new ContextLoaderListener(wac));


        String p = System.getenv("PORT");
        int port = p == null ? DEFAULT_PORT : Integer.parseInt(p);
        Server server = new Server(port);
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
