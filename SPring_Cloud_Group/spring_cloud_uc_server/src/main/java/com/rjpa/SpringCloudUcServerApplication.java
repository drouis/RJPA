package com.rjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableFeignClients("com.rjpa.feign")
public class SpringCloudUcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudUcServerApplication.class, args);
    }

    @Configuration
    @ImportResource(locations = {"classpath: bootstrap-*.yml"})
    public class CustomPropertySourceLocator {
    }
}