package com.librarymanagement.repository;

import com.librarymanagement.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository <Author, Integer> {
    List<Author> findAuthorsByBooks(int bookId);
}
