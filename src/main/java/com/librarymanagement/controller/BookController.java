package com.librarymanagement.controller;

import com.librarymanagement.model.Author;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/books")
    public ResponseEntity<Object> getList() {
        return bookService.getAll();
    }

    @RequestMapping("/book/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable int id) {
        return bookService.getBookById(id);

    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateBookById(@PathVariable int id, @RequestBody Book book) {
        System.out.println(book.toString());
        return bookService.updateBookById(book, id);

    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBookById(@PathVariable int id) {
        return bookService.deleteBookById(id);

    }

    @RequestMapping(value = "/author/{authorId}/books")
    public ResponseEntity<Object> getAllBooksByAuthorId(@PathVariable int authorId) {
        if (authorRepository.findById(authorId).isEmpty()) {

            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        List<Book> bookList = bookRepository.findBooksByAuthorsId(authorId);

        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @RequestMapping(value = "/author/{authorId}/book", method = RequestMethod.POST)
    public ResponseEntity<Object> addBookByAuthorId(@PathVariable int authorId, @RequestBody Book book) {
        if (!authorRepository.existsById(authorId)) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        Author author = authorRepository.findById(authorId).get();
        final boolean[] inList = {false};
        final boolean[] newBook = {true};

        // save book if not present in book table
        bookRepository.findAll().forEach(book1 -> {
            if (book1.getTitle().equals(book.getTitle())
                    && book1.getYearOfPublished() == book.getYearOfPublished()
                    && book1.getSerialNumber() == book.getSerialNumber()) {
                System.out.println("Not new book");
                newBook[0] = false;
            }
        });

        // add to book database if not present
        if (!newBook[0]) {
            System.out.println("Not New book");
            System.out.println("Book already exist" + HttpStatus.CREATED);
        } else {
            bookRepository.save(book);
        }


        // check if book exist in author books list
        bookRepository.findBooksByAuthorsId(authorId).forEach(book1 -> {
            boolean exist = book1.getTitle().equals(book.getTitle())
                    && book1.getYearOfPublished() == book.getYearOfPublished()
                    && book1.getSerialNumber() == book.getSerialNumber();
            if (exist) {
                // do nothing
                inList[0] = true;
                System.out.println("Book already present in author book list");

            }
        });


        if (inList[0])
            return new ResponseEntity<>("Book exist in author book list", HttpStatus.OK);

        if (!inList[0]) {

            System.out.println("Not in list");
            author.addBook(book);
            authorRepository.save(author);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    // TODO get all Authors by Bookdid

//    public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId) {
//        if (!tagRepository.existsById(tagId)) {
//            throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
//        }
//        List<Tutorial> authors = authorRepository.findTutorialsByTagsId(tagId);
//        return new ResponseEntity<>(authors, HttpStatus.OK);
//    }


// TODO
//    @DeleteMapping("/authors/{authorId}/tags/{tagId}")
//    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "tagId") Long tagId) {
//        Tutorial author = authorRepository.findById(authorId)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + authorId));
//
//        author.removeTag(tagId);
//        authorRepository.save(author);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


}
