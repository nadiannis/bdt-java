//package com.nadiannis.bookapp;
//
//import com.nadiannis.bookapp.model.Book;
//
//import org.apache.catalina.connector.Response;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import io.restassured.RestAssured;
////import io.restassured.RestAssured.*;
////import io.restassured.matcher.RestAssuredMatchers.*;
////import org.hamcrest.Matchers.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class BookappLiveTest {
//
//    private static final String API_ROOT = "http://localhost:4000/api/books";
//
//    @Test
//    public void whenGetAllBooks_thenOK() {
//        final io.restassured.response.Response response = RestAssured.get(API_ROOT);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenGetBooksByTitle_thenOK() {
//        final Book book = createRandomBook();
//        createBookAsUri(book);
//
//        final io.restassured.response.Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        assertTrue(response.as(List.class).size() > 0);
//    }
//
//    @Test
//    public void whenGetCreatedBookById_thenOK() {
//        final Book book = createRandomBook();
//        final String location = createBookAsUri(book);
//
//        final io.restassured.response.Response response = RestAssured.get(location);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        assertEquals(book.getTitle(), response.jsonPath().get("title"));
//    }
//
//    @Test
//    public void whenGetNotExistBookById_thenNotFound() {
//        final io.restassured.response.Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
//    }
//
//    // POST
//    @Test
//    public void whenCreateNewBook_thenCreated() {
//        final Book book = createRandomBook();
//
//        final io.restassured.response.Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .post(API_ROOT);
//        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenInvalidBook_thenError() {
//        final Book book = createRandomBook();
//        book.setAuthor(null);
//
//        final io.restassured.response.Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .post(API_ROOT);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenUpdateCreatedBook_thenUpdated() {
//        final Book book = createRandomBook();
//        final String location = createBookAsUri(book);
//
//        book.setId(Long.parseLong(location.split("api/books/")[1]));
//        book.setAuthor("newAuthor");
//        io.restassured.response.Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .put(location);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        response = RestAssured.get(location);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        assertEquals("newAuthor", response.jsonPath()
//                .get("author"));
//
//    }
//
//    @Test
//    public void whenDeleteCreatedBook_thenOk() {
//        final Book book = createRandomBook();
//        final String location = createBookAsUri(book);
//
//        io.restassured.response.Response response = RestAssured.delete(location);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        response = RestAssured.get(location);
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
//    }
//
//    // ===============================
//
//    private Book createRandomBook() {
//        final Book book = new Book();
//        book.setTitle(randomAlphabetic(10));
//        book.setAuthor(randomAlphabetic(15));
//        return book;
//    }
//
//    private String createBookAsUri(Book book) {
//        final io.restassured.response.Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .post(API_ROOT);
//        return API_ROOT + "/" + response.jsonPath()
//                .get("id");
//    }
//
//}
