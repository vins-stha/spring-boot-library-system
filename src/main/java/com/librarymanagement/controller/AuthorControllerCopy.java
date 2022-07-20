package com.librarymanagement.controller;

import com.librarymanagement.model.Author;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorControllerCopy {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping("/")
    public String index() {
        System.out.println("Hello from authors");
        return "Hello from authors";
    }

    @RequestMapping("/authors")
    public ResponseEntity<Object> getList() {
        List<Author> authors = new ArrayList<>();

        for (Author author : authorRepository.findAll()) {
            System.out.println(author.toString());
            authors.add(author);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @RequestMapping("/author/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable int id) {
        System.out.println("ID=" + id);
        System.out.println("author exist=" + authorRepository.findById(id) + " after get" +  authorRepository.findById(id).get());
        System.out.println("STREAM=, + " +  authorRepository.findById(id).stream());


        Author author = authorRepository.findById(id).get();
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(value = "/author/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        System.out.println(author.toString());
        return authorService.addAuthor(author);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateAuthorById(@PathVariable int id, @RequestBody Author author) {
        System.out.println(author.toString());
        return authorService.updateAuthorById(author, id);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAuthorById(@PathVariable int id) {
        return authorService.deleteAuthor(id);
    }


}
