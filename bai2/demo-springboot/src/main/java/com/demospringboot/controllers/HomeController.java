package com.demospringboot.controllers;

import com.demospringboot.dto.LangOptionDTO;
import com.demospringboot.dto.response.OpenWeatherResponse;
import com.demospringboot.services.OpenWeatherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class HomeController {
    private final OpenWeatherService openWeatherService;

    public HomeController(OpenWeatherService openWeatherService, RestTemplate restTemplate) {
        this.openWeatherService = openWeatherService;
    }
    @GetMapping({"/","/home"})
    public String showHomePage(Model model, Locale locale) {
        // Weather
        OpenWeatherResponse ow = openWeatherService.getWeatherCurrent("Hanoi");
        double temp = Math.ceil(ow.getMain().getTemp() - 273);
        model.addAttribute("temp", temp);

        // Languages
        List<LangOptionDTO> langs = List.of(
                new LangOptionDTO("en", "English"),
                new LangOptionDTO("vi", "Tiếng Việt")
        );
        model.addAttribute("langs", langs);
        model.addAttribute("currentLang", locale.getLanguage());

        return "index";
    }
}
