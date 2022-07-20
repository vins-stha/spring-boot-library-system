package com.librarymanagement.repository;

import com.librarymanagement.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAuthorsByBooksId(int bookId);
}
