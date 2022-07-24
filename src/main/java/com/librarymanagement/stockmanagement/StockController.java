package com.librarymanagement.stockmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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
