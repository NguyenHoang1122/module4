package com.dictionary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DictionaryController {
    private static final Map<String, String> dictionary = new HashMap<>();
    static {
        dictionary.put("hello", "xin chào");
        dictionary.put("goodbye", "tạm biệt");
        dictionary.put("thank you", "cảm ơn");
        dictionary.put("yes", "vâng");
        dictionary.put("no", "không");
    }

    @GetMapping("/")
    public String showDictionary() {
        return "index";
    }

    @PostMapping("/translate")
    public String translateDictionary(@RequestParam("word") String word,
                                      Model model) {
        String translation = dictionary.get(word.toLowerCase());
        if (translation != null) {
            model.addAttribute("word", word);
            model.addAttribute("translation", translation);
        } else {
            model.addAttribute("word", word);
            model.addAttribute("message", "Không tìm thấy từ" + word +" trong từ điển");
        }
        return "result";
    }
}
