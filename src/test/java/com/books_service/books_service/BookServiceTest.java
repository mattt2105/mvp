package com.books_service.books_service;

import com.books_service.books_service.controller.BookController;
import com.books_service.books_service.model.Book;
import com.books_service.books_service.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookServiceTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setTitle("Pemrograman Java");
        book.setAuthor("John Doe");
        book.setPublishYear(2023);
        book.setCategory("Teknologi");
        book.setAvailableCopies(5);
    }

    @Test
    public void testAddBook() {
        Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.addBook(book);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Pemrograman Java", response.getBody().getTitle());
        verify(bookService, times(1)).addBook(book);
    }

    @Test
    public void testGetBookById() {
        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBook(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Pemrograman Java", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = Arrays.asList(book);
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testUpdateBook() {
        Mockito.when(bookService.updateBook(Mockito.any(Long.class), Mockito.any(Book.class))).thenReturn(new ResponseEntity<>(book, HttpStatus.OK));

        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Pemrograman Java", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook(1L, book);
    }

    @Test
    public void testDeleteBook() {
        Mockito.when(bookService.deleteBook(1L)).thenReturn(new ResponseEntity<>("book has been deleted", HttpStatus.OK));

        ResponseEntity<String> response = bookController.deleteBook(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("book has been deleted", response.getBody());
        verify(bookService, times(1)).deleteBook(1L);
    }
}
