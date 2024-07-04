package com.nadiannis.reactive_r2dbc;

import com.nadiannis.reactive_r2dbc.controller.BookController;
import com.nadiannis.reactive_r2dbc.dto.ErrorResponse;
import com.nadiannis.reactive_r2dbc.entity.Book;
import com.nadiannis.reactive_r2dbc.exception.BookNotFoundException;
import com.nadiannis.reactive_r2dbc.repository.BookRepository;
import com.nadiannis.reactive_r2dbc.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookController.class)
@AutoConfigureWebTestClient
@Slf4j
public class BookControllerTest {

    private final WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    ConnectionFactoryInitializer initializer;

    private final String url = "/api/books";

    @Autowired
    public BookControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void findBookByIdOk() {
        var book = Book.builder()
                .id(1)
                .title("test title 1")
                .description("test description 1")
                .build();
        log.info("book: {}", book);

        when(bookService.findById(book.getId())).thenReturn(Mono.just(book));

        String findBookByIdUrl = String.format("%s/%s", url, book.getId());
        log.info("url: {}", findBookByIdUrl);
        webTestClient
                .get()
                .uri(findBookByIdUrl)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Book.class)
                .consumeWith(result -> {
                    var existBook = result.getResponseBody();

                    assert existBook != null;
                    assertEquals(book.getId(), existBook.getId());
                    assertEquals(book.getTitle(), existBook.getTitle());
                });
    }

    @Test
    void findBookByIdErrorNotFound() {
        var book = Book
                .builder()
                .id(3)
                .title("test title 3")
                .description("test description 3")
                .build();

        when(bookService.findById(book.getId())).thenReturn(
                Mono.error(new BookNotFoundException(String.format("book with id %s not found", book.getId())))
        );

        String findTodoByIdUrl = String.format("%s/%s", url, book.getId());
        webTestClient
                .get()
                .uri(findTodoByIdUrl)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class)
                .consumeWith(result -> {
                    var errorResponse = result.getResponseBody();

                    assertTrue(errorResponse.getStatusCode() !=  HttpStatus.OK.value());
                    assertEquals(String.format("book with id %s not found", book.getId()), errorResponse.getMessage());
                });
    }
}
