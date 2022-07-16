package com.librarymanagement.controller;

import com.librarymanagement.model.Book;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.repository.BookRepository;
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

        return bookService.getAllBooksByAuthorId(authorId);
    }

    @RequestMapping(value = "/author/{authorId}/book", method = RequestMethod.POST)
    public ResponseEntity<Object> addBookByAuthorId(@PathVariable int authorId, @RequestBody Book book) {
        if (!authorRepository.existsById(authorId)) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }

        return bookService.addBookByAuthorId(authorId, book);
    }

    @RequestMapping(value = "/author/{authorId}/book/{bookId}")
    public ResponseEntity<Object> getABookByAuthorId(@PathVariable("authorId") int authorId, @PathVariable("bookId") int bookId)
    {
        if (!authorRepository.existsById(authorId)) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        return bookService.getABookByAuthorId(authorId, bookId);
    }

    @RequestMapping(value = "/author/{authorId}/book/{bookId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateABookByAuthorId(@PathVariable("authorId") int authorId, @PathVariable("bookId") int bookId, @RequestBody Book book)
    {
        if (!authorRepository.existsById(authorId)) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        return bookService.updateABookByAuthorId(authorId, bookId, book);
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
