package com.rjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.rjpa.client")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringCloudUcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudUcServerApplication.class, args);
    }

}