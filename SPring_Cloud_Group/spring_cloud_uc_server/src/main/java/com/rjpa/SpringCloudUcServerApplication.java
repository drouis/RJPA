package com.rjpa;

import com.rjpa.config.ApplicationPermissionScanStartup;
import com.rjpa.service.InitPermissionIntoDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@EnableCaching
@EnableEurekaClient
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients("com.rjpa.feign")
@SpringBootApplication(scanBasePackages = "com.rjpa.**")
@EnableAspectJAutoProxy
public class SpringCloudUcServerApplication extends SpringBootServletInitializer implements CommandLineRunner {
    protected final static Logger logger = LoggerFactory.getLogger(SpringCloudUcServerApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringCloudUcServerApplication.class);
        springApplication.addListeners(new ApplicationPermissionScanStartup());
        springApplication.run(args);
    }

    @Configuration
    @ImportResource(locations = {"classpath: bootstrap-*.yml"})
    public class CustomPropertySourceLocator {
        //TODO 加入AOP 启动扫描注册全部权限注解类，并加入到权限表
    }

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        String SPRING_CONFIG_LOCATION = "spring.config.location";
        final PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        final List<Resource> resourceLst = new ArrayList<Resource>();
        String configFilePath = System.getProperty(SPRING_CONFIG_LOCATION);
        resourceLst.add(new FileSystemResource("classpath:application.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap-admin.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap-jpa.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap-rabbitmq.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap-redis.yml"));
        resourceLst.add(new FileSystemResource("classpath:bootstrap-thymeleaf.yml"));
        configurer.setLocations(resourceLst.toArray(new Resource[]{}));
        return configurer;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Override
    public void run(String... strings) throws Exception {
        initPermissionIntoDBService.initPermissionIntoDBService();
    }

    @Autowired
    InitPermissionIntoDBService initPermissionIntoDBService;
}