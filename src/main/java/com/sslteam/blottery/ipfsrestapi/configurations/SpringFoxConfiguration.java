package com.sslteam.blottery.ipfsrestapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ServerBuilder;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * author: Sergey Khaylov
 * email: <a href="mailto:info@proextlab.ru">info@proextlab.ru</a>
 * org: ProextLab
 * site: <a href="https://proextlab.ru">ProextLab</a>
 **/

@Configuration
public class SpringFoxConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("IPFS REST API")
                        .build()
                )
                .tags(
                        new Tag("File", "Работа с файлами")
                )
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }
}
