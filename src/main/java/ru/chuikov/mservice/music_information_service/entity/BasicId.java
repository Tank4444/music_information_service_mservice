package ru.chuikov.mservice.music_information_service.entity;

import java.io.Serializable;

public abstract class BasicId implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
