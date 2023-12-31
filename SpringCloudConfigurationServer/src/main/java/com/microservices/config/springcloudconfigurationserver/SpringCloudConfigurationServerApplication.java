package com.microservices.config.springcloudconfigurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigurationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigurationServerApplication.class, args);
    }

}
