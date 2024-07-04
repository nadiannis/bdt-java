package com.nadiannis.spring_onetomany.service;

import com.nadiannis.spring_onetomany.dto.OwnerFormDto;
import com.nadiannis.spring_onetomany.model.Owner;
import com.nadiannis.spring_onetomany.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private OwnerRepository repository;

    @Autowired
    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
    }

    public List<Owner> findAll() {
        return repository.findAll();
    }

    public Owner add(OwnerFormDto body) {
        Owner owner = new Owner();
        owner.setName(body.getName());
        owner.setEmail(body.getName());
        return repository.save(owner);
    }

    public Owner findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("owner not found"));
    }

}
