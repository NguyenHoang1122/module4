package com.formvalidationregistration.service;

import com.formvalidationregistration.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}