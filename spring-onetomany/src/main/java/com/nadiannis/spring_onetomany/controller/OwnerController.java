package com.nadiannis.spring_onetomany.controller;

import com.nadiannis.spring_onetomany.dto.OwnerFormDto;
import com.nadiannis.spring_onetomany.model.Owner;
import com.nadiannis.spring_onetomany.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerService service;

    @GetMapping
    public List<Owner> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Owner add(@RequestBody OwnerFormDto body) {
        return service.add(body);
    }

    @GetMapping("/{id}")
    public Owner getById(@PathVariable Long id) {
        return service.findById(id);
    }

}
