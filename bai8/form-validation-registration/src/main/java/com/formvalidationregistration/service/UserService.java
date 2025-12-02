package com.formvalidationregistration.service;

import com.formvalidationregistration.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements  IUserService {
    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    public List<User> findAllUser() {
        return users;
    }
}
