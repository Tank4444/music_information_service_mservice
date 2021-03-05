package ru.chuikov.mservice.music_information_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {

    ANONYMOUS("ANONYMOUS"),
    USER("USER"),
    ADMIN("ADMIN");


    @Getter private String value;
}
