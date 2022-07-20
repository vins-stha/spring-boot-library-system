package com.librarymanagement.controller;

import com.librarymanagement.model.Author;
import com.librarymanagement.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {

        return  authorService.getAllAuthors();
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable("id") int id) {

        return authorService.getAuthorById(id);
    }

    @PostMapping("/authors")
    public ResponseEntity<Object> createAuthor(@RequestBody Author author) {
        if (author.getFname().isEmpty() || author.getLname().isEmpty())
            return new ResponseEntity<>("Required field/s empty", HttpStatus.BAD_REQUEST);

        return authorService.addAuthor(author);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") int id, Author author) {

        return authorService.updateAuthorById(author, id);
    }


    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") int id) {

        return authorService.deleteAuthor(id);
    }

}

