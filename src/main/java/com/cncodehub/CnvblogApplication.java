package com.cncodehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CnvblogApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(CnvblogApplication.class, args);
    }

}
