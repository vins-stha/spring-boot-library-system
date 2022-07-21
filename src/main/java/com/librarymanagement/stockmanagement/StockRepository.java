package com.librarymanagement.stockmanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {
    @Query(value = "SELECT * from Stock stock where stock.book_id = ?", nativeQuery = true)
    Stock findByBookId(int bookId);

    @Query(value = "Delete from Stock stock where stock.book_id=?", nativeQuery = true)
    void deleteByBookId(int bookId);

}
