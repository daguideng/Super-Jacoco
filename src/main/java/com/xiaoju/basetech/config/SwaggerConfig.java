package com.xiaoju.basetech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8899}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        String baseUrl = "http://localhost:" + serverPort;
        return new OpenAPI()
                .info(new Info()
                        .title("第三方服务API文档")
                        .description("基于Spring Boot的代码覆盖率服务接口文档")
                        .version("1.0")
                        .contact(new Contact()
                                .name("API支持")
                                .url(baseUrl)
                                .email("")))
                .servers(List.of(new Server()
                        .url(baseUrl)
                        .description("本地开发环境")));
    }

}