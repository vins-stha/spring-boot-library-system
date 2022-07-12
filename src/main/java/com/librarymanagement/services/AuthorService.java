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

        public ResponseEntity <Object> getAll() {
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


}
