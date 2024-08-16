package com.books_service.books_service.service;

import com.books_service.books_service.model.Book;
import com.books_service.books_service.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book addBook(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        Book data = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return data;
    }

    public List<Book> getAllBooks(){
        List<Book> datas = bookRepository.findAll();
        return datas;
    }

    public ResponseEntity<Book> updateBook(Long id, Book book){
        Optional<Book> existingdata = bookRepository.findById(id);
        if (existingdata.isPresent()){
            Book updateData = existingdata.get();

            updateData.setTitle(book.getTitle());
            updateData.setAuthor(book.getAuthor());
            updateData.setPublishYear(book.getPublishYear());
            updateData.setCategory(book.getCategory());
            updateData.setAvailableCopies(book.getAvailableCopies());
            bookRepository.save(updateData);

            return new ResponseEntity(updateData, HttpStatus.OK);
        }
        return new ResponseEntity("book doesnt exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteBook(Long id){
        Optional<Book> existingdata = bookRepository.findById(id);
        if (existingdata.isPresent()){
            bookRepository.delete(existingdata.get());

            return new ResponseEntity("book has been deleted",HttpStatus.OK);
        }
        return new ResponseEntity("book doesnt exist",HttpStatus.BAD_REQUEST);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }
        if (book.getPublishYear() == null || book.getPublishYear() == 0) {
            throw new IllegalArgumentException("Publish year must more than 0");
        }
        if (book.getCategory() == null || book.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (book.getAvailableCopies() == null || book.getAvailableCopies() == 0) {
            throw new IllegalArgumentException("Available copies must more than 0");
        }
    }
}
