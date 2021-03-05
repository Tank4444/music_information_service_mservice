package ru.chuikov.mservice.music_information_service.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.web.header.Header;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.Collections;

@Configuration
@Log
public class WebClientConfig {
    @Value("${access.username}")
    private String username;

    @Value("${access.password}")
    private String password;

    @Value("${user.information.host}")
    private String host;

    @Value("${user.information.port}")
    private String port;

    @Bean
    public WebClient webClient(){
        WebClient result = WebClient.builder()
                .baseUrl("http://"+host+":"+port)
                .defaultHeaders(header -> {
                    header.setBasicAuth(username, password);
                    header.setContentType(MediaType.APPLICATION_JSON);
                })
                .defaultUriVariables(Collections.singletonMap("url", "http://"+host+":"+port))
                .build();
        log.info("baseurl "+ host+":"+port);
        log.info("basicAuth "+ username+":"+password);
        log.info("result "+ result.toString());
        return result;
    }
    @Bean
    public String basicAuthString(){
        return "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes());
    }
}
