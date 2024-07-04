package com.nadiannis.bookapp.controller.api;

import java.util.List;
import java.util.Optional;

import com.nadiannis.bookapp.exception.BookIdMismatchException;
import com.nadiannis.bookapp.exception.BookNotFoundException;
import com.nadiannis.bookapp.exception.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nadiannis.bookapp.model.Book;
import com.nadiannis.bookapp.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List<Book> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        Book newBook = new Book();

        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());

        return bookRepository.save(newBook);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book) {
        Book savedBook = bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);

        if (savedBook.getId() != id) {
            throw new BookIdMismatchException();
        }

        savedBook.setAuthor(book.getAuthor());
        savedBook.setTitle(book.getTitle());

        return bookRepository.save(savedBook);
    }
}
