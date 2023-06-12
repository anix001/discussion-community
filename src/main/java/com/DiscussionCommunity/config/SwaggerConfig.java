package com.DiscussionCommunity.config;

import com.DiscussionCommunity.utils.SwaggerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private final SwaggerUtil swaggerUtil;

    public SwaggerConfig(SwaggerUtil swaggerUtil) {
        this.swaggerUtil = swaggerUtil;
    }

    @Bean
    public Docket api(){
       return new Docket(DocumentationType.SWAGGER_2)
               .apiInfo(swaggerUtil.getInfo())
               .securityContexts(swaggerUtil.securityContextList())
               .securitySchemes(List.of(swaggerUtil.apiKey()))
               .select()
               .apis(RequestHandlerSelectors.any())
               .paths(PathSelectors.any())
               .build();
    }


}
