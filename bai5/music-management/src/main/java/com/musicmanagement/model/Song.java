package com.musicmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên bài hát không được để trống")
    @Size(max = 800, message = "Tên bài hát không vượt quá 800 ký tự")
    @Pattern(
            regexp = "^[^@;,.=\\-+]+$",
            message = "Tên bài hát không chứa ký tự đặc biệt"
    )
    private String name;
    @NotBlank(message = "Nghệ sĩ không được để trống")
    @Size(max = 300, message = "Tên nghệ sĩ không vượt quá 300 ký tự")
    @Pattern(
            regexp = "^[^@;,.=\\-+]+$",
            message = "Tên nghệ sĩ không chứa ký tự đặc biệt"
    )
    private String artist;
    @NotBlank(message = "Thể loại nhạc không được để trống")
    @Size(max = 1000, message = "Thể loại nhạc không vượt quá 1000 ký tự")
    @Pattern(
            regexp = "^[a-zA-ZÀ-ỹ\\s,]+$",
            message = "Thể loại chỉ được phép chứa chữ cái, khoảng trắng và dấu phẩy"
    )
    private String category;
    private String filePath;

    public Song() {
    }

    public Song(Long id, String name, String artist, String category, String filePath) {
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", category='" + category + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
