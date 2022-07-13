package com.librarymanagement.repository;

import org.springframework.data.repository.CrudRepository;
import com.librarymanagement.model.Author;

import java.util.*;
public interface AuthorRepository extends CrudRepository <Author, Integer> {

}
