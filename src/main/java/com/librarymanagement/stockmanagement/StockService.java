package com.librarymanagement.stockmanagement;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private ArrayList<Stock> stockList = new ArrayList<>();

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private BookRepository bookRepository;

    public StockService() {
    }

    public ResponseEntity<Object> getBooksStock() {
        for (Stock stock : stockRepository.findAll()) {
            stockList.add(stock);

        }
        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getBookCount(@PathVariable int bookId) {
        int book_count = 0;
        if (stockRepository.findByBookId(bookId) != null) {

            book_count = stockRepository.findByBookId(bookId).getCount();

        }
        return new ResponseEntity<>(book_count, HttpStatus.OK);
    }

    public ResponseEntity<Object> addBookCount(@PathVariable int bookId, @RequestBody StockRequest stockRequest) {
        Stock _stock;
        // update the count if already exist
        if (stockRepository.findByBookId(bookId) != null) {
            _stock = stockRepository.findByBookId(bookId);
            int present_count = stockRepository.findByBookId(bookId).getCount();
            present_count = present_count + stockRequest.getBookCount();

            if (present_count < 0) {
                present_count = 0;
            }

            _stock.setCount(present_count);

        }
        // create new entry if not exist
        else {
            _stock = new Stock();
            _stock.setBook(bookRepository.findById(bookId).get());
            _stock.setCount(stockRequest.getBookCount());
        }
        stockRepository.save(_stock);
        return new ResponseEntity<>(_stock, HttpStatus.OK);
    }

    public void loanABookAndUpdateStock(@RequestBody int bookId) {
        Stock _stock;

        if (stockRepository.findByBookId(bookId) != null) {
            _stock = stockRepository.findByBookId(bookId);
            int present_count = stockRepository.findByBookId(bookId).getCount() - 1;

            if (present_count < 0) {
                present_count = 0;
            }

            _stock.setCount(present_count);

        } else {
            throw new NotFoundException("Book not present in stock");
        }

    }

    public ResponseEntity<Object> deleteEntryByBookId(@PathVariable int bookId) {
        if (stockRepository.findByBookId(bookId) != null) {
            try {
                stockRepository.deleteById(stockRepository.findByBookId(bookId).getId());
            } catch (Exception e) {
                System.out.println("Something went wrong. " + e.getMessage());
            }
        } else {
            throw new NotFoundException("Entry not found for book id = " + bookId);
        }
        return new ResponseEntity<>("Deleted entry", HttpStatus.OK);
    }
}
