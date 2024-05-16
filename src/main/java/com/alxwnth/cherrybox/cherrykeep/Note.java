package com.alxwnth.cherrybox.cherrykeep;

import jakarta.persistence.*;

import java.util.Date;

@Entity
class Note {
    private @Id
    @GeneratedValue Long id;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    public Note() {}

    public Note(String text, Date expiresAt) {
        this.text = text;
        this.createdAt = new Date();
        this.expiresAt = expiresAt;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}