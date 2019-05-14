package com.rjpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
/**
 * @ClassName:
 * @Description:
 * @parm
 * @return
 * @author: drouis
 * @date:   2019/5/15 00:47
 *  https://blog.csdn.net/w1054993544/article/details/80679853
 */
public class SwaggerConfiguragtion {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rjpa.mic"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SPring Eureka 微服务说明文档")
                .description("---- API接口说明 --------------------------------")
                .termsOfServiceUrl("")
                .contact(new Contact("Drouis", "drouis@ofim.com.cn", "drouis@ofim.com.cn"))
                .version("1.0").build();
    }
}
