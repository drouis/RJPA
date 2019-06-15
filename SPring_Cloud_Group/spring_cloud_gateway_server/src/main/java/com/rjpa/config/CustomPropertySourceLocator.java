package com.rjpa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath: bootstrap-*.yml"})
public class CustomPropertySourceLocator {

    private static final Logger log = LoggerFactory.getLogger(CustomPropertySourceLocator.class);

}