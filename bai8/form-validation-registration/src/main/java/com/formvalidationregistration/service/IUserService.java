package com.formvalidationregistration.service;

import com.formvalidationregistration.model.User;
import java.util.List;

public interface IUserService {
    void save(User user);
    List<User> findAll();
}