package com.librarymanagement.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowedItem;
import com.librarymanagement.model.User;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedItemRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.requests.BorrowRequests;
import com.librarymanagement.stockmanagement.StockService;

@RestController
public class BorrowedItemController {
    private ArrayList<BorrowedItem> borrowedItems = new ArrayList<>();

    @Autowired
    BorrowedItemRepository borrowedItemRepository;

    @Autowired
    UserRepository UserRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    StockService stockService;

    @GetMapping("/users/{userId}/borrowedItems")
    public ResponseEntity<Object> getAllItemsBorrowedByUser(@PathVariable int userId) {

        UserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        borrowedItemRepository.getAllItemsBorrowedByUser(userId).forEach(borrowedItems::add);

        return new ResponseEntity<>(borrowedItems, HttpStatus.OK);
    }

    @GetMapping("/borrowedItems")
    public ResponseEntity<Object> getAllItems() {

        ArrayList<BorrowedItem> _borrowedItems = new ArrayList<>();
        borrowedItemRepository.findAll().forEach(_borrowedItems::add);

        return new ResponseEntity<>(_borrowedItems, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/borrow")
    public ResponseEntity<Object> borrowItem(@PathVariable int userId, @RequestBody BorrowRequests request) {

        String message = "";
        // check user id
        UserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        // check book id
        bookRepository.findById(request.getBookId()).orElseThrow(() -> new NotFoundException("Book not found"));

        Book _bookToBorrow = bookRepository.findById(request.getBookId()).get();
        User _borrower = UserRepository.findById(userId).get();

        // check if item exist in their list
        ArrayList<BorrowedItem> itemsBorrowed = borrowedItemRepository.getAllItemsBorrowedByUser(userId);
        BorrowedItem _item;
        for (BorrowedItem item : itemsBorrowed) {
            // if book exist
            if (item.getBook().getId() == request.getBookId()) {
                message = "Book/Item already exist in borrowed item list";
            } else {
                Date today = new Date();
                long dueDay = today.getTime() + (14 * 24 * 60 * 60 * 1000);
                Date dueDate = new Date(dueDay);

                _item = new BorrowedItem();
                _item.setBook(_bookToBorrow);
                _item.setUser(_borrower);
                _item.setStartState(today);
                _item.setDueDate(dueDate);

                // save to borrwed list
                borrowedItemRepository.save(_item);

                // Reduce its count in stock by 1
                stockService.loanABookAndUpdateStock(_bookToBorrow.getId());
                message = _item.toString();

            }
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // @PostMapping("/users/{userId}/borrow")
    // V2.0 wehn UI is present
    // public ResponseEntity<Object> borrowItem(@PathVariable int userId,
    // @RequestBody Book book) {

}
