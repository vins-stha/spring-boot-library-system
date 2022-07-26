package com.librarymanagement.services;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.model.Author;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {
    private ArrayList<Book> books;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookService() {
        this.books = new ArrayList<>();
    }

    public ResponseEntity<Object> getAllBooks() {
        List<Book> books = new ArrayList<Book>();

        bookRepository.findAll().forEach(books::add);
        int count = 1;
        for(Book book: bookRepository.findAll()){
            System.out.println(count + ": "+ book.hashCode());
        }

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllBooksByAuthorId(int authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NotFoundException("Not found Author with id = " + authorId);
        }

        List<Book> books = bookRepository.findBooksByAuthorsId(authorId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<Object> getBooksById( int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Book with id = " + id));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllAuthorsByBookId(int bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException("Not found Book  with id = " + bookId);
        }

        List<Author> authors = authorRepository.findAuthorsByBooksId(bookId);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    public ResponseEntity<Object> addBook(int authorId, Book bookRequest) {
        Book book = authorRepository.findById(authorId).map(author -> {
            // System.out.println("AUTHRO" + author.getFname() + author.getClass() + "stream" );
            int bookId = bookRequest.getId();

            // book exists
            if (bookId != 0) {
                Book _book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new NotFoundException("Not found Book with id = " + bookId));
                author.addBook(_book);
                authorRepository.save(author);
                return _book;
            }

            // add and create new Book
            author.addBook(bookRequest);
            return bookRepository.save(bookRequest);
        }).orElseThrow(() -> new NotFoundException("Not found Author with id = " + authorId));

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateBook( int id, Book book) {
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

    public ResponseEntity<HttpStatus> deleteBookFromAuthor( int authorId,int bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Not found Author with id = " + authorId));

        author.removeBook(bookId);
        authorRepository.save(author);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> deleteBook(int id) {
        bookRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
