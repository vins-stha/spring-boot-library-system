package com.librarymanagement.controller;

import com.librarymanagement.model.Author;
import com.librarymanagement.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping("/authors/")
    public String index() {
        System.out.println("Hello from authors");
        return "Hello from authors";
    }

    @RequestMapping("/authors/all")
    public ResponseEntity<Object> getList() {
        return authorService.getAll();
    }

    @RequestMapping("/author/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);

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
        return authorService.deleteAuthorById(id);

    }


}
