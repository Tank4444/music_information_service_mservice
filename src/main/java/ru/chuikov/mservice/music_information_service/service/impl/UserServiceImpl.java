package ru.chuikov.mservice.music_information_service.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.chuikov.mservice.music_information_service.entity.User;
import ru.chuikov.mservice.music_information_service.service.UserService;

import java.util.Optional;

@Service
@Log
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.information.user.byId}")
    private String pathId;

    @Value("${user.information.user.byUsername}")
    private String pathUsername;

    @Value("${user.information.user.byEmail}")
    private String pathEmail;

    @Value("${user.information.method}")
    private String mathod;

    private final WebClient webClient;

    @Autowired
    private UriBuilder uriBuilder;

    @Autowired
    private String basicAuthString;

    @Autowired
    public UserServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<User> getUserByIdAsync(Long id) {
        return null;
    }

    @Override
    public Mono<User> getUserByUsernameAsync(String username) {
        return null;
    }

    @Override
    public Mono<User> getUserByEmailAsync(String email) {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        log.info("Call get user by id = "+id);
        Optional<User> result=Optional.ofNullable(webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(pathId)
                        .build(id)
                )
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                .retrieve()
                .bodyToMono(User.class)
                .block());
        log.info("Request ok");
        return result;
    }

    @Override
    @Transactional
    public Optional<User> getUserByUsername(String username) {
        log.info("Call get user by username = "+username);
        Optional<User> result=Optional.ofNullable(webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(pathUsername)
                        .build(username)
                )
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                .retrieve()
                .bodyToMono(User.class)
                .block());
        log.info("Request ok");
        return result;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.info("Call get user  by email = "+email);
        Optional<User> result=Optional.ofNullable(webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(pathEmail)
                        .build(email)
                )
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                .retrieve()
                .bodyToMono(User.class)
                .block());
        log.info("Request ok");
        return result;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Start load user by username with ' "+s+" '");
        return getUserByUsername(s).map( user ->
                org.springframework.security.core.userdetails.User.withUsername(
                        user.getUsername()).
                        password(user.getPassword()).
                        authorities(new SimpleGrantedAuthority("ROLE_"+user.getRole())).build()
        ).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
