package com.codegym.demo.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean checkAccount(String username, String password) {
        // Simulate account check logic
        // In a real application, this would involve checking against a database or an external service
        return "admin".equals(username) && "1234".equals(password);
    }
}
