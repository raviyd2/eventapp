package com.eventapp.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.eventapp.eventapp")
@EntityScan(basePackages = "com.eventapp.eventapp")
@ComponentScan(basePackages = "com.eventapp.eventapp")
public class EventappApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EventappApplication.class, args);
        } catch (Exception e) {
            System.err.println("APPLICATION FAILED TO START");
            e.printStackTrace();
        }
    }
}