package com.librarymanagement.services;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.model.Author;
import com.librarymanagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Service
public class AuthorService {
    private ArrayList<Author> authors;

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorService() {
        this.authors = new ArrayList<>();
    }


    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = new ArrayList<Author>();
        for(Author author : authorRepository.findAll())
            authors.add(author);

        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


    public ResponseEntity<Object> getAuthorById(@PathVariable("id") int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Author with id = " + id));

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        if (author.getFname().isEmpty() || author.getLname().isEmpty())
            return new ResponseEntity<>("Required field is empty", HttpStatus.BAD_REQUEST);
        authorRepository.save(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }


    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") int id) {
        if (authorRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);
        authorRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/authors")
    public ResponseEntity<HttpStatus> deleteAllAuthors() {
        authorRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> updateAuthorById(Author author, int id) {
        if (!authorRepository.findById(id).isEmpty()) {
            Author old = authorRepository.findById(id).get();

            if(!author.getFname().isEmpty())
                old.setFname(author.getFname());
            if(!author.getMname().isEmpty())
                old.setMname(author.getMname());
            if(!author.getLname().isEmpty())
                old.setLname(author.getLname());
            if(!author.getDOB().toString().isEmpty())
                old.setDOB(author.getDOB());
            if(!author.getCountry().isEmpty())
                old.setCountry(author.getCountry());

            authorRepository.save(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);

    }
}
