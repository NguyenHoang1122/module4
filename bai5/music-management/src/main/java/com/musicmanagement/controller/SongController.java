package com.musicmanagement.controller;


import com.musicmanagement.model.Song;
import com.musicmanagement.model.SongForm;
import com.musicmanagement.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;

    @Value("${file-upload}")
    private String uploadDirectory;

    @GetMapping("")
    public String index(Model model) {
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "/list";
    }

    private boolean isMusicFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("audio/mpeg") ||      // mp3
                        contentType.equals("audio/mp4") ||       // m4a / mp4 audio
                        contentType.equals("audio/wav") ||       // wav
                        contentType.equals("audio/x-wav") ||
                        contentType.equals("video/mp4")          // mp4 video
        );
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("songForm", new SongForm());
        return "/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute SongForm form) {

        Song song = new Song();
        song.setName(form.getName());
        song.setArtist(form.getArtist());
        song.setCategory(form.getCategory());

        MultipartFile file = form.getFilePath();

        if (file != null && !file.isEmpty()) {
            if (!isMusicFile(file)) {
                throw new RuntimeException("Chỉ được upload file nhạc (mp3, mp4, wav, m4a)");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadDirectory + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            song.setFilePath("/uploads/" + fileName);
        }

        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {

        Song song = songService.findById(id);

        SongForm form = new SongForm();
        form.setId(song.getId());
        form.setName(song.getName());
        form.setArtist(song.getArtist());
        form.setCategory(song.getCategory());

        model.addAttribute("songForm", form);
        model.addAttribute("oldFile", song.getFilePath());

        return "/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute SongForm form) {

        Song song = songService.findById(id);
        song.setName(form.getName());
        song.setArtist(form.getArtist());
        song.setCategory(form.getCategory());

        MultipartFile file = form.getFilePath();

        // Nếu người dùng upload file mới → ghi đè file cũ
        if (file != null && !file.isEmpty()) {

            if (!isMusicFile(file)) {
                throw new RuntimeException("Chỉ được upload file nhạc (mp3, mp4, wav, m4a)");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadDirectory + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            song.setFilePath("/uploads/" + fileName);
        }

        songService.save(song);
        return "redirect:/songs";
    }


}
