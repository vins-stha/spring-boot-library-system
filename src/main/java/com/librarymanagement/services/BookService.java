package com.librarymanagement.services;

import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    private ArrayList<Book> books;

    @Autowired
    private BookRepository bookRepository;

    public BookService() {
        this.books = new ArrayList<>();
    }

    public ResponseEntity<Object> getAll() {
        this.books = new ArrayList<>();
        for (Book book : bookRepository.findBooksByDistinctTitle()) {
            this.books.add(book);
        }
        return new ResponseEntity<>(this.books, HttpStatus.OK);
    }

    public ResponseEntity<Object> getBookById(int id) {
        if (!bookRepository.findById(id).isEmpty()) {
            Book book = bookRepository.findById(id).get();

            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> addBook(Book book) {
        if (book.getTitle().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateBookById(Book book, int id) {
        if (!bookRepository.findById(id).isEmpty()) {
            Book old = bookRepository.findById(id).get();

            old.setPrice(book.getPrice());
            old.setSerialNumber(book.getSerialNumber());
            old.setTitle(book.getTitle());
            old.setYearOfPublished(book.getYearOfPublished());
            old.setAuthors(book.getAuthors());

            bookRepository.save(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Object> deleteBookById(int id) {
        if (bookRepository.findById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        bookRepository.deleteById(id);
        return new ResponseEntity<>("Book deleted", HttpStatus.NO_CONTENT);
    }

}
