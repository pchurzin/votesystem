package ru.pchurzin.votesystem;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.web.SpringServletContainerInitializer;

import javax.servlet.ServletContainerInitializer;

import static org.eclipse.jetty.servlet.listener.ContainerInitializer.ServletContainerInitializerServletContextListener;

public class Main {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.getServletContext().setExtendedListenerTypes(true);
        ServletContainerInitializer springServletContainerInitializer = new SpringServletContainerInitializer();
        ServletContainerInitializerServletContextListener scil = new ServletContainerInitializerServletContextListener(springServletContainerInitializer);
        scil.addClasses(MvcInitializer.class, SecurityInitializer.class);
        servletContextHandler.addEventListener(scil);

        String p = System.getenv("PORT");
        int port = p == null ? DEFAULT_PORT : Integer.parseInt(p);
        Server server = new Server(port);
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
