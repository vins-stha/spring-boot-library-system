package com.librarymanagement.services;

import com.librarymanagement.model.Author;
import com.librarymanagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorServiceCopy {
    private ArrayList<Author> authors;

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorServiceCopy() {
        this.authors = new ArrayList<>();
    }

    public ResponseEntity<Object> getAll() {
        System.out.println("AUthors=" + authorRepository.findById(6).get());
//        for (Author author : authorRepository.findAll()) {
//            System.out.println(author.toString());
//            authors.add(author);
//        }
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
            return new ResponseEntity<>("Required field is empty", HttpStatus.BAD_REQUEST);
        authorRepository.save(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
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

    public ResponseEntity<Object> deleteAuthorById(int id) {
        if (authorRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);
        authorRepository.deleteById(id);
        return new ResponseEntity<>("Author deleted", HttpStatus.NO_CONTENT);
    }

}
