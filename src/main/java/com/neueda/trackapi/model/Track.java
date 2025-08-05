package com.neueda.trackapi.model;

import java.time.LocalDateTime;

public class Track {
    private String id;
    private String title;
    private String artist;
    private Integer duration;
    private LocalDateTime creationDate;

    public Track() {
    }

    public Track(String title, String artist, Integer duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.creationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}