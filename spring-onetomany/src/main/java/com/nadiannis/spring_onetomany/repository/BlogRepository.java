package com.nadiannis.spring_onetomany.repository;

import com.nadiannis.spring_onetomany.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
