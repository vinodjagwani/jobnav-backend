package com.jobnav.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Application {

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}
