package com.librarymanagement.repository;

import com.librarymanagement.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findBooksByAuthorsId(int authorId);

    @Query(value = "select * from Books where id in (select  min(id) from Books group by title)", nativeQuery = true)
    List<Book> findBooksByDistinctTitle();

}
