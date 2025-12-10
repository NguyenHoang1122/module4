package com.mailconfigmanager.controller;

import com.mailconfigmanager.model.MailConfig;
import com.mailconfigmanager.service.IMailConfigService;
import com.mailconfigmanager.service.MailConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mail-config")
public class MailConfigController {
    private IMailConfigService mailConfigService =  new MailConfigService();

    @GetMapping("/")
    public String listMailConfig(Model model) {
        model.addAttribute("mailConfigs", mailConfigService.findAll());
        return "index";
    }

    @GetMapping("/info")
    public String viewInfo(@RequestParam("id") int id, Model model) {
        MailConfig config = mailConfigService.findById(id);
        model.addAttribute("config", config);
        return "mail/info";
    }

    @GetMapping("/edit")
    public String editMailConfig(@RequestParam("id") int id, Model model) {
        model.addAttribute("mailConfig", mailConfigService.findById(id));
        model.addAttribute("languages", new String[]{"English", "Vietnamese", "Japanese", "Chinese"});
        model.addAttribute("pageSize", new int[]{5, 10, 15, 25, 50, 100});
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("config") MailConfig config) {
        mailConfigService.update(config.getId(), config);
        return "redirect:/mail-config";
    }
}
