package com.musicmanagement.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private Long id;

    private String name;
    private String artist;
    private String category;
    private MultipartFile filePath;

    public SongForm() {
    }

    public SongForm(Long id, String name, String artist, String category, MultipartFile filePath) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.category = category;
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getFilePath() {
        return filePath;
    }

    public void setFilePath(MultipartFile filePath) {
        this.filePath = filePath;
    }
}
