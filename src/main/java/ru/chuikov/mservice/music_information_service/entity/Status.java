package ru.chuikov.mservice.music_information_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
    OPEN("OPEN"),
    BLOCKED("BLOCKED");

    @Getter private String value;
}
