package com.visual.open.anpr.server.bootstrap.conf;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
@EnableKnife4j
public class Knife4jConfig {

    @Value("${visual.swagger.enable:true}")
    private Boolean enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(new ApiInfoBuilder()
                    .title("车牌识别服务API")
                    .description("车牌识别服务API")
                    .version("1.0.0")
                    .build())
                .groupName("1.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.visual.open.anpr.server.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}


