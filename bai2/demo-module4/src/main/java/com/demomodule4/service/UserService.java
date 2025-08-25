package com.demomodule4.service;

import com.demomodule4.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(1, "admin", "123456", "ADMIN", "admin@example.com", "0962388745"));
        users.add(new User(2, "hoang", "hoang123", "USER", "hoang@example.com", "0962333587"));
        users.add(new User(3, "chien", "chien123", "USER", "chien@example.com", "0974211548"));
        users.add(new User(4, "tam", "tam123", "USER", "tam@example.com", "0895215547"));

        return users;
    }
}