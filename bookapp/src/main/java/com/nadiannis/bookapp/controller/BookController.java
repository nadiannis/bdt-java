package com.nadiannis.bookapp.controller;

import com.nadiannis.bookapp.model.Book;
import com.nadiannis.bookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public String bookListView(Model model) {
        Iterable<Book> books = bookRepository.findAllByOrderByIdDesc();
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/books/add")
    public String bookAddView(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    @PostMapping("/books/add")
    public String bookAdd(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String bookDetailView(@PathVariable Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(b -> model.addAttribute("book", b));;
        return "books/detail";
    }

    @GetMapping("/books/{id}/edit")
    public String bookEditView(@PathVariable long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(b -> model.addAttribute("book", b));
        return "books/edit";
    }

    @PutMapping("/books/{id}/edit")
    public String bookEdit(@PathVariable long id, @ModelAttribute Book book, Model model) {
        Book savedBook = bookRepository.findById(id).get();

        savedBook.setTitle(book.getTitle());
        savedBook.setAuthor(book.getAuthor());

        bookRepository.save(savedBook);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/books/{id}")
    public String bookDelete(@PathVariable long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return "redirect:/books/" + id;
        }

        bookRepository.deleteById(id);
        return "redirect:/books";
    }

}
