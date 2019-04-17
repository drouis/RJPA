package com.rjpa.cross;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * ftl视图
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * 静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/statics/assets/");
        super.addResourceHandlers(registry);
    }
}