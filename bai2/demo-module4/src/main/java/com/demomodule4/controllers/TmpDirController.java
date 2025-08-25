package com.demomodule4.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TmpDirController {

    @GetMapping("/tmpdir")
    public String getTmpDir() {
        return "Tomcat đang dùng thư mục tạm: " + System.getProperty("java.io.tmpdir");
    }
}