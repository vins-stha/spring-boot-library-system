package com.librarymanagement.stockmanagement;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping("/stocks/books")
    public ResponseEntity<Object> getBooksStock() {

        return stockService.getBooksStock();
    }

    @GetMapping("stocks/books/{bookId}")
    public ResponseEntity<Object> getBookCount(@PathVariable int bookId) {

        return stockService.getBookCount(bookId);
    }

    @PostMapping("/stocks/books/{bookId}")
    public ResponseEntity<Object> addBookCount(@PathVariable int bookId, @RequestBody StockRequest stockRequest) {
        return stockService.addBookCount(bookId, stockRequest);
    }

    @DeleteMapping("/stocks/books/{bookId}")
    public ResponseEntity<Object> deleteEntryByBookId(@PathVariable int bookId) {
        return stockService.deleteEntryByBookId(bookId);
    }
}
