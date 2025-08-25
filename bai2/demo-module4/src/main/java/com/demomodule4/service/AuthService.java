package com.demomodule4.service;


import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("123456")) {
            return true;
        }
        return false;
    }
}
