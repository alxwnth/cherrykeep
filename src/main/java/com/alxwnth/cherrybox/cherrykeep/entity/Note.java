package com.alxwnth.cherrybox.cherrykeep.entity;

import jakarta.persistence.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
public class Note {
    private @Id
    @GeneratedValue Long id;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime expiresAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public Note() {
    }

    public Note(String text, User user) {
        this.text = text;
        this.user = user;
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC+0"));
        this.createdAt = now;
        this.expiresAt = now.plusDays(1);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public Long getId() {
        return id;
    }
}