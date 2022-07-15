package com.librarymanagement.services;

import com.librarymanagement.model.Author;
import com.librarymanagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorService {
    private ArrayList<Author> authors;

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorService() {
        this.authors = new ArrayList<>();
    }

    public ResponseEntity<Object> getAll() {

        for (Author author : authorRepository.findAll()) {
            System.out.println(author.toString());
            authors.add(author);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAuthorById(int id) {
        if (!authorRepository.findById(id).isEmpty()) {
            Author author = authorRepository.findById(id).get();

            return new ResponseEntity<>(author, HttpStatus.OK);
        }

        return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> addAuthor(Author author) {
        if (author.getFname().isEmpty() || author.getLname().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        authorRepository.save(author);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateAuthorById(Author author, int id) {
        if (!authorRepository.findById(id).isEmpty()) {
            Author old = authorRepository.findById(id).get();

            old.setFname(author.getFname());
            old.setMname(author.getMname());
            old.setLname(author.getLname());
            old.setDOB(author.getDOB());
            old.setCountry(author.getCountry());

            authorRepository.save(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Object> deleteAuthorById(int id) {
        if (authorRepository.findById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        authorRepository.deleteById(id);
        return new ResponseEntity<>("Author deleted", HttpStatus.NO_CONTENT);
    }

}
