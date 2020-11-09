package org.bmstu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Spring boot application class
 * @Import(RepositoryRestMvcConfiguration.class) annotation imports Spring MVC controller's
 * collection, json converters, needed to implement restful interface.
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
@SpringBootApplication
public class Application {

    /**
     * Application entry point method
     * @param args String array
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}