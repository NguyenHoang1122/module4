package com.springusermodel.DAO;

import com.springusermodel.model.Login;
import com.springusermodel.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final List<User> users;

    static {
        users = new ArrayList<>();
        users.add(new User("john", "123456", "John", "john@codegym.vn", 18));
        users.add(new User("bill", "123456", "Bill", "bill@codegym.vn", 20));
        users.add(new User("mike", "123456", "Mike", "mike@codegym.vn", 22));
    }

    public static User checkLogin(Login login) {
        for (User user : users) {
            if (user.getAccount().equals(login.getAccount())
                    && user.getPassword().equals(login.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
