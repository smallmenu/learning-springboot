package com.niuchaoqun.springboot.security.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Bean
    public Docket createApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.niuchaoqun.springboot.security.controller.api"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createOpenapiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("openapi")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.niuchaoqun.springboot.security.controller.openapi"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createBasicDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("basic")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.niuchaoqun.springboot.security.controller.basic"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot security")
                .termsOfServiceUrl("http://niuchaoqun.com")
                .version("1.0")
                .build();
    }
}
