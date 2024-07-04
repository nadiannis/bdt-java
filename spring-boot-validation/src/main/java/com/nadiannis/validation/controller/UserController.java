package com.nadiannis.validation.controller;

import java.util.List;

import com.nadiannis.validation.model.User;
import com.nadiannis.validation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @GetMapping
    public HttpEntity<List<User>> listAll() {
        return new ResponseEntity<> (service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<User> getOne(@PathVariable("id") String userId) {

        User user = service.get(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping
    public HttpEntity<User> add(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") String userId) {
        service.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
