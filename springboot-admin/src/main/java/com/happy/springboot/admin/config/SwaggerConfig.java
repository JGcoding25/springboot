package com.happy.springboot.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2配置类
 *
 * @author Happy
 * @date 2019/5/16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 控制是否开启swagger
     */
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    /**
     * 添加摘要信息Docket
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：接口文档")
                        .description("描述：后台开放接口描述")
                        .contact(new Contact("Happy","",""))
                        .version("版本号:1.0.0")
                        .build())
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.happy.springboot.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
