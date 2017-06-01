package com.budingge.springbootlogbackmultienvdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootLogbackMultiEnvDemoApplication {


    public static final Logger logger = LoggerFactory.getLogger(SpringBootLogbackMultiEnvDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogbackMultiEnvDemoApplication.class, args);

        logger.trace("this is trace level!!");
        logger.debug("this is debug level!!");
        logger.info("this is info level!!");
        logger.warn("this is warn level!!");
        logger.error("this is error level!!");

    }
}
