package ru.chuikov.mservice.music_information_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;
import ru.chuikov.mservice.music_information_service.entity.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Mono<User> getUserByIdAsync(Long id);
    Mono<User> getUserByUsernameAsync(String username);
    Mono<User> getUserByEmailAsync(String email);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
}
