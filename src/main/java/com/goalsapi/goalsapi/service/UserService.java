package com.goalsapi.goalsapi.service;

import com.goalsapi.goalsapi.entity.User;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    User saveUser(User user);
}
