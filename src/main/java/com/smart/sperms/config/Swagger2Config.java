package com.smart.sperms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descript swagger2配置类
 * @Date 2018-07-25 10:03:31
 * @Version 1.0
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenParam = new ParameterBuilder();
            tokenParam.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                    .required(true).build();

        List<Parameter> params = new ArrayList<>();
        params.add(tokenParam.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //api文档扫描覆盖的包
                .apis(RequestHandlerSelectors.basePackage("com.smart.sperms.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params);
    }

    //构建api文档的详细描述参数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("【SPERMS】后台模块API文档")
                .version("1.0")
                .description("Api描述")
                .build();
    }

}
