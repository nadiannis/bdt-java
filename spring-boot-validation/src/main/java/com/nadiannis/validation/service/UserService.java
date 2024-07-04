package com.nadiannis.validation.service;

import com.nadiannis.validation.model.User;

import java.util.List;

public interface UserService {

    List<User> listAll();

    User get(String userId);

    User save(User user);

    void delete(String userId);

}
