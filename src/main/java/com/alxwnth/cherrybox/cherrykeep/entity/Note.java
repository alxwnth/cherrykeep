package com.alxwnth.cherrybox.cherrykeep.entity;

import jakarta.persistence.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text", nullable = false, length = 500)
    private String text;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime expiresAt;

    @Column(name = "pinned", nullable = false)
    private boolean pinned;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Note() {
    }

    public Note(String text, User user) {
        this.text = text;
        this.user = user;
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC+0"));
        this.createdAt = now;
        this.expiresAt = now.plusDays(1);
        this.pinned = false;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void pin() {
        this.pinned = true;
    }

    public void unpin() {
        this.pinned = false;
    }
}