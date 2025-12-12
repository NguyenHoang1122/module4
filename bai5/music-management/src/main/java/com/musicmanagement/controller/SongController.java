package com.musicmanagement.controller;


import com.musicmanagement.model.Song;
import com.musicmanagement.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;

    @GetMapping("")
    public String index(Model model) {
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "/list";
    }

}
