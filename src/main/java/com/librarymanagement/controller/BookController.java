package com.librarymanagement.controller;

import com.librarymanagement.model.Book;
import com.librarymanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<Object> getAllBooks() {

        return bookService.getAllBooks();
    }

    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<Object> getAllBooksByAuthorId(@PathVariable(value = "authorId") int authorId) {
       
        return bookService.getAllBooksByAuthorId(authorId);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBooksById(@PathVariable(value = "id") int id) {

        return bookService.getBooksById(id);
    }

    @GetMapping("/books/{bookId}/authors")
    public ResponseEntity<Object> getAllAuthorsByBookId(@PathVariable(value = "bookId") int bookId) {

        return bookService.getAllAuthorsByBookId(bookId);
    }

    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<Object> addBook(@PathVariable(value = "authorId") int authorId, @RequestBody Book book) {

        return bookService.addBook(authorId, book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") int id, @RequestBody Book book) {

        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<HttpStatus> deleteBookFromAuthor(@PathVariable(value = "authorId") int authorId,
                                                            @PathVariable(value = "bookId") int bookId) {
        return bookService.deleteBookFromAuthor(authorId, bookId);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        return bookService.deleteBook(id);
    }
}
