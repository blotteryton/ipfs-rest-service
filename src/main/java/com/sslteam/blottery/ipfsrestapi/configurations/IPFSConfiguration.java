package com.sslteam.blottery.ipfsrestapi.configurations;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

/**
 * author: Sergey Khaylov
 * email: <a href="mailto:info@proextlab.ru">info@proextlab.ru</a>
 * org: ProextLab
 * site: <a href="https://proextlab.ru">ProextLab</a>
 * created: 22.08.2022
 **/

@Configuration
@ConfigurationProperties(prefix = "ipfs")
@Data
public class IPFSConfiguration {
    private String host;
    private String apiUrl;
    private Integer timeout;

    @Bean
    public WebClient ipfsWebClient() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(apiUrl);
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(getTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(getTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(apiUrl)
                .build();
    }
}
