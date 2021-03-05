package ru.chuikov.mservice.music_information_service.entity;

import lombok.Builder;
import lombok.ToString;

//@Builder(toBuilder = true)
@ToString
public class User extends BasicId {

    private String username;

    private String email;

    private String password;

    private Role role;

    private Status status;

    public User() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
