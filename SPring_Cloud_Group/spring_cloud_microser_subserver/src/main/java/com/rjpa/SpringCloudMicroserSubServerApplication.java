package com.rjpa;

import Entity.AdminPermissionV;
import com.rjpa.config.ApplicationPermissionScanStartup;
import com.rjpa.feign.client.PermissionServiceFeignClient;
import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients("com.rjpa.feign")
public class SpringCloudMicroserSubServerApplication extends SpringBootServletInitializer implements CommandLineRunner {
    protected final static Logger logger = LoggerFactory.getLogger(SpringCloudMicroserSubServerApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringCloudMicroserSubServerApplication.class);
        springApplication.addListeners(new ApplicationPermissionScanStartup());
        springApplication.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Configuration
    @ImportResource(locations = {"classpath: bootstrap-*.yml"})
    public class CustomPropertySourceLocator {
        //TODO 加入AOP 启动扫描注册全部权限注解类，并加入到权限表
    }

    @Override
    public void run(String... strings) throws Exception {
        List<AdminPermissionV> permissionVList = new ApplicationPermissionScanStartup().getPermissionsList();
        Result r = Result.ok(permissionVList);
        permissionServiceFeignClient.postSubMicoServerPermissions(r);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    PermissionServiceFeignClient permissionServiceFeignClient;
}