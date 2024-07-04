package com.nadiannis.spring_onetomany.service;

import com.nadiannis.spring_onetomany.dto.BlogFormDto;
import com.nadiannis.spring_onetomany.model.Blog;
import com.nadiannis.spring_onetomany.model.Owner;
import com.nadiannis.spring_onetomany.repository.BlogRepository;
import com.nadiannis.spring_onetomany.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private BlogRepository blogRepository;

    private OwnerRepository ownerRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, OwnerRepository ownerRepository) {
        this.blogRepository = blogRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog add(BlogFormDto body) {
        Long ownerId = body.getOwnerId();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new EntityNotFoundException("owner not found"));

        Blog blog = new Blog();
        blog.setTitle(body.getTitle());
        blog.setCategory(body.getCategory());
        blog.setContent(body.getContent());
        blog.setOwner(owner);

        return blogRepository.save(blog);
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("blog not found"));
    }

}
