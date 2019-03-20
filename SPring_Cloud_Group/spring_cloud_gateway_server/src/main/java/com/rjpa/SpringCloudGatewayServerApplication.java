package com.rjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableZuulProxy
@EnableHystrix
public class SpringCloudGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayServerApplication.class, args);
    }

}