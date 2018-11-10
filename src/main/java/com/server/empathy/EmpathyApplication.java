package com.server.empathy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EmpathyApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:application-dev.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(EmpathyApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);


//        SpringApplication.run(EmpathyApplication.class, args);
    }
}
