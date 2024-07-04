package com.nadiannis.bookapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nadiannis.bookapp.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);
    public List<Book> findAllByOrderByIdDesc();

}
